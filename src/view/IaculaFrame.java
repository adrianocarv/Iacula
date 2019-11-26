package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import util.Config;

public class IaculaFrame extends JFrame {

	private JLabel componenteImagem = new JLabel();
	private JTextArea componenteFrase = new JTextArea("frase");

	private Container container = getContentPane();

	public IaculaFrame(){
		super(Config.VERSAO);
		setAlwaysOnTop(true);

		//constrói o container
		container.setLayout(new BorderLayout(5,5));

		//constrói os componentes
		//imagem
		
		//frase
		componenteFrase.setEditable(false);
		componenteFrase.setBackground(null);
	}
	
	public void atualizaComponentesFrame(ImageIcon iaculaImagem, String iaculaFrase) throws Exception{
		//container.removeAll();
		//atualiza os componentes
		//imagem
		JLabel componenteImagem = new JLabel();
		if(iaculaImagem != null)
			componenteImagem = new JLabel(iaculaImagem, SwingConstants.CENTER);
		
		//frase
		int style = 0;
		if(Config.getIaculaFraseFonteNegrito()) style += Font.BOLD;
		if(Config.getIaculaFraseFonteItalico()) style += Font.ITALIC;
		Font font = new Font(Config.getIaculaFraseFonteNome(),style, Config.getIaculaFraseFonteTamanho());
		componenteFrase.setEditable(false);
		componenteFrase.setFont(font);
		componenteFrase.setText(iaculaFrase+" ");

		//adiciona os componentes ao container
		container.removeAll();
		dispose();
		container.add(componenteImagem,BorderLayout.CENTER);
		container.add(componenteFrase,BorderLayout.SOUTH);
	}
	
	public void exibirAlerta() throws Exception {
		// exibe o alerta
//		setVisible(false);
		pack();
		setVisible(true);
	}
	
	public void ocultarAlerta(){
		setVisible(false);
	}

}
