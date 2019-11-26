package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import model.IaculaRuntimeException;
import util.Config;
import controller.IaculaController;

public class AngelusFrame extends JFrame {

	private IaculaController iaculaController = IaculaController.getInstance();
	
	private JLabel componenteImagem = new JLabel();
	private JTextArea componenteTexto = new JTextArea("texto");
	private JButton angelusNextButton;
	private JButton angelusPreviusButton;

	private Container container = getContentPane();

	public AngelusFrame(){
		super(Config.VERSAO);
		setAlwaysOnTop(true);
		setLocation(new Point(200,0));

		//constrói o container
		container.setLayout(new BorderLayout(5,5));

		//constrói os componentes
		//imagem
		
		//texto
		componenteTexto.setEditable(false);
		componenteTexto.setBackground(null);

		//botoes
		angelusNextButton = new JButton(">>");
		angelusPreviusButton = new JButton("<<");
		
		AngelusHandler handler = new AngelusHandler();
		angelusNextButton.addActionListener(handler);
		angelusPreviusButton.addActionListener(handler);
	}
	
	public void atualizaComponentesFrame(ImageIcon angelusImagem, String angelusTexto){
		//atualiza os componentes
		//imagem
		JLabel componenteImagem = new JLabel();
		if(angelusImagem != null)
			componenteImagem = new JLabel(angelusImagem,SwingConstants.CENTER);
		
		//texto
		int style = 0;
		if(Config.getAngelusTextoFonteNegrito()) style += Font.BOLD;
		if(Config.getAngelusTextoFonteItalico()) style += Font.ITALIC;
		Font font = new Font(Config.getAngelusTextoFonteNome(),style, Config.getAngelusTextoFonteTamanho());
		componenteTexto.setEditable(false);
		componenteTexto.setFont(font);
		componenteTexto.setText(angelusTexto+" ");

		//adiciona os componentes ao container
		container.removeAll();
		dispose();
		container.add(componenteImagem,BorderLayout.NORTH);
		container.add(componenteTexto,BorderLayout.SOUTH);
		container.add(angelusNextButton,BorderLayout.EAST);
		container.add(angelusPreviusButton,BorderLayout.WEST);
	}
	
	public void exibirAlerta() throws Exception {
		// exibe o alerta
		pack();
		setVisible(true);
	}
	
	public void ocultarAlerta(){
		setVisible(false);
	}

	private class AngelusHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				if(e.getSource() == angelusNextButton){
					iaculaController.incrementarIndiceAngelus();
					iaculaController.exibirAlertaAngelus();
					exibirAlerta();
				}else if(e.getSource() == angelusPreviusButton){
					iaculaController.decrementarIndiceAngelus();
					iaculaController.exibirAlertaAngelus();
					exibirAlerta();
				}
			} catch (Exception e1) {
				throw new IaculaRuntimeException(e1.getMessage());
			}
		}
	}
}
