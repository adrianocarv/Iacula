package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import model.IaculaRuntimeException;
import util.Config;
import util.Utils;

public class SobreFrame extends JFrame {

	private JLabel iconeLabel;
	private JTextArea textoTextArea;
	private JButton okButton;

	private Container container = getContentPane();

	public SobreFrame(){
		super("Sobre o "+Config.VERSAO);
		setAlwaysOnTop(true);
		setLocation(new Point(300,300));

		//constr�i o container
		container.setLayout(new BorderLayout(5,5));

		//constr�i os componentes
		//�cone
		ImageIcon imageIcon = new ImageIcon(Config.getCaminhoRecursos()+Config.nomeArquivoIconeIacula);
		iconeLabel = new JLabel(imageIcon);
			
		//texto
		String texto = "\nProjeto "+Config.VERSAO+".";
		texto += "\n\nSoftware livre. Autorizada reprodu��o e distribui��o.";
		texto += "\n\n�ltima atualiza��o: "+Config.DATA_VERSAO+".";
		texto += "\n\nSugest�es e contato: projetoiacula@gmail.com";
		textoTextArea = new JTextArea(texto);
		textoTextArea.setText(texto);
		textoTextArea.setEditable(false);
		textoTextArea.setBackground(null);
		
		//bot�es
		okButton = new JButton("OK");
		
		SobreHandler handler = new SobreHandler();
		okButton.addActionListener(handler);

		//adiciona os componentes ao container
		container.add(iconeLabel,BorderLayout.WEST);
		container.add(textoTextArea,BorderLayout.CENTER);
		container.add(okButton,BorderLayout.SOUTH);
	}
	
	private class SobreHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				if(e.getSource() == okButton){
					setVisible(false);
				}
			} catch (Exception e1) {
				throw new IaculaRuntimeException(e1.getMessage());
			}
		}
	}

	public void exibirAlerta() throws Exception {
		// exibe o alerta
		pack();
		setVisible(true);
	}
}
