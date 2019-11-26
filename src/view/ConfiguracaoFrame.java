package view;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.IaculaRuntimeException;
import util.Config;
import util.Utils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.IaculaController;
import entity.ConfigEntity;

public class ConfiguracaoFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private IaculaController iaculaController = IaculaController.getInstance();
	String[] listaOrdenacao = {Config.ORDENA_POR_NOME,Config.ORDENA_POR_DATA,Config.ORDEM_ALEATORIA};
	
	//Componentes Visuais
	//IACULA - Geral
	private JCheckBox iaculaHabilitado = new JCheckBox();
	private JTextField iaculaFrequenciaExibicao = new JFormattedTextField(new DecimalFormat("###"));
	private JTextField iaculaTempoExibicao = new JFormattedTextField(new DecimalFormat("###"));
 
	//IACULA - Frases
	private JCheckBox iaculaFraseRandomica = new JCheckBox();
	private JComboBox iaculaFraseUnico = new JComboBox();
	private JCheckBox iaculaFraseDiaSemana = new JCheckBox();
	private JCheckBox iaculaFraseContinuaUltima = new JCheckBox();
	private JComboBox iaculaFraseFonteNome = new JComboBox(this.getFontNames());
	private JTextField iaculaFraseFonteTamanho = new JFormattedTextField(new DecimalFormat("###"));
	private JCheckBox iaculaFraseFonteNegrito = new JCheckBox();
	private JCheckBox iaculaFraseFonteItalico = new JCheckBox();
	private JTextField iaculaFraseLargura = new JFormattedTextField(new DecimalFormat("###"));
 
	//IACULA - Imagens
	private JCheckBox iaculaImagemRecuperaGrupo = new JCheckBox();
	private JComboBox iaculaImagemOrdenacao = new JComboBox(listaOrdenacao);
	private JCheckBox iaculaImagemContinuaUltima = new JCheckBox();
	private JTextField iaculaImagemLargura = new JFormattedTextField(new DecimalFormat("###"));

	//ANGELUS - Geral
	private JCheckBox angelusHabilitado = new JCheckBox();
	private JTextField angelusTempoExibicao = new JFormattedTextField(new DecimalFormat("###"));
	private JTextField angelusExibirComAntecedencia = new JFormattedTextField(new DecimalFormat("###"));
	private JTextField angelusDataTempoPascal = new JFormattedTextField(new SimpleDateFormat("dd/MM"));
	private JComboBox angelusFusoHorario = new JComboBox(this.getFusosHorarios());

	//ANGELUS - Texto
	private JComboBox angelusTextoFonteNome = new JComboBox(this.getFontNames());
	private JTextField angelusTextoFonteTamanho = new JFormattedTextField(new DecimalFormat("###"));
	private JCheckBox angelusTextoFonteNegrito = new JCheckBox();
	private JCheckBox angelusTextoFonteItalico = new JCheckBox();
	private JTextField angelusTextoLargura = new JFormattedTextField(new DecimalFormat("###"));

	//ANGELUS - Imagem
	private JComboBox angelusImagemOrdenacao = new JComboBox(listaOrdenacao);
	private JTextField angelusImagemAltura = new JFormattedTextField(new DecimalFormat("###"));
 
	//Botões
	private JButton salvarButton = new JButton("Salvar");
	private JButton fraseButton = new JButton("Frases...");
	private JButton textoAngelusButton = new JButton("Angelus...");
	private JButton pastaImagemButton = new JButton("Imagens...");
	private JButton cancelarButton = new JButton(" Cancelar  ");
	
	public ConfiguracaoFrame() {
		super();
		this.setSize(590,600);
		setLocation(new Point(100,80));

		//constrói o container
		super.setLayout(new FlowLayout());
		
		//constrói os componentes
		
		//adiciona o Handler aos botões
		ConfiguracaoHandler handler = new ConfiguracaoHandler();
		salvarButton.addActionListener(handler);
		cancelarButton.addActionListener(handler);
		pastaImagemButton.addActionListener(handler);
		fraseButton.addActionListener(handler);
		textoAngelusButton.addActionListener(handler);

		carregaLegendaComponentes();

		//adiciona os componentes ao container
		FormLayout formLayout = this.getFormLayout();
		CellConstraints cc = new CellConstraints();
		PanelBuilder builder = this.getPanelBuilder(formLayout, cc);
		this.add(builder.getPanel());

		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
				int opt = JOptionPane.showConfirmDialog(null, "As alterações podem não terem sido salvas. Deseja salvar as alterações?", Config.VERSAO, JOptionPane.YES_NO_OPTION);
				if (opt == JOptionPane.OK_OPTION) {
					salvarAction();
				}
            }
        });
	}
	
	public void exibirAlerta() throws Exception {
		// exibe o alerta
		setTitle(Config.VERSAO+" - Configurações"+" ("+Utils.getDataHoraFormatada(new Date())+")");
		carregarIaculaFraseUnicoJComboBox();
		carregaFrameValoresArquivo();
		setVisible(true);
	}

	private FormLayout getFormLayout(){
		FormLayout formLayout = new FormLayout("right:70dlu,2dlu,60dlu,2dlu,right:60dlu,2dlu,75dlu", // colunas (x, max=7, uma por virgula)
				   "pref," +  // linha 1 (y, max=51, uma por virgula)
				   "2dlu," +  // 2
				   "pref," +  // 3
				   "2dlu," +  // 4
				   "pref," +  // 5
				   "2dlu," +  // 6
				   "pref," +  // 7
				   "2dlu," +  // 8
				   "pref," +  // 9
				   "2dlu," +  // 10
				   "pref," +  // 11
				   "2dlu," +  // 12
				   "pref," +  // 13
				   "2dlu," +  // 14
				   "pref," +  // 15
				   "2dlu," +  // 16
				   "pref," +  // 17
				   "2dlu," +  // 18
				   "pref," +  // 19
				   "2dlu," +  // 20
				   "pref," +  // 21
				   "2dlu," +  // 22
				   "pref," +  // 23
				   "2dlu," +  // 24
				   "pref," +  // 25
				   "2dlu," +  // 26
				   "pref," +  // 27
				   "2dlu," +  // 28
				   "pref," +  // 29
				   "2dlu," +  // 30
				   "pref," +  // 31
				   "2dlu," +  // 32
				   "pref," +  // 33
				   "2dlu," +  // 34
				   "pref," +  // 35
				   "2dlu," +  // 36
				   "pref," +  // 37
				   "2dlu," +  // 38
				   "pref," +  // 39
				   "2dlu," +  // 40
				   "pref," +  // 41
				   "2dlu," +  // 42
				   "pref," +  // 43
				   "2dlu," +  // 44
				   "pref," +  // 45
				   "2dlu," +  // 46
				   "pref," +  // 47
				   "2dlu," +  // 48
				   "pref," +  // 49
				   "2dlu," +  // 50
				   "pref"     // 51
				   );
		return formLayout;
	}

	private PanelBuilder getPanelBuilder(FormLayout formLayout, CellConstraints cc){
		PanelBuilder builder = new PanelBuilder(formLayout);

		builder.addSeparator("Iacula",		 							cc.xyw(1,1,7));
		builder.add(buildElementJLabel("Habilitado"), 					cc.xy(1,3));
		builder.add(iaculaHabilitado, 									cc.xy(3,3));
		builder.add(buildElementJLabel("Freqüência de exibição"), 		cc.xy(1,5));
		builder.add(iaculaFrequenciaExibicao, 							cc.xy(3,5));
		builder.add(buildElementJLabel("Tempo de exibição"), 			cc.xy(5,5));
		builder.add(iaculaTempoExibicao, 								cc.xy(7,5));
		
		builder.add(buildSubTitleJLabel("Frases",40),					cc.xy(1,7));
		builder.add(buildElementJLabel("Ordem randômica"), 				cc.xy(1,9));
		builder.add(iaculaFraseRandomica, 								cc.xy(3,9));
		builder.add(buildElementJLabel("Grupo de execução"), 			cc.xy(5,9));
		builder.add(buildJComboBox(iaculaFraseUnico),					cc.xy(7,9));
		builder.add(buildElementJLabel("Execução dia da semana"),		cc.xy(1,11));
		builder.add(iaculaFraseDiaSemana, 								cc.xy(3,11));
		builder.add(buildElementJLabel("Continua da última"), 			cc.xy(5,11));
		builder.add(iaculaFraseContinuaUltima, 							cc.xy(7,11));
		builder.add(buildElementJLabel("Tamanho da fonte"),				cc.xy(1,13));
		builder.add(iaculaFraseFonteTamanho, 							cc.xy(3,13));
		builder.add(buildElementJLabel("Fonte"),						cc.xy(5,13));
		builder.add(buildJComboBox(iaculaFraseFonteNome),				cc.xy(7,13));
		builder.add(buildElementJLabel("Negrito"), 						cc.xy(1,15));
		builder.add(iaculaFraseFonteNegrito, 							cc.xy(3,15));
		builder.add(buildElementJLabel("Itálico"), 						cc.xy(5,15));
		builder.add(iaculaFraseFonteItalico, 							cc.xy(7,15));
		builder.add(buildElementJLabel("Largura máxima"),				cc.xy(1,17));
		builder.add(iaculaFraseLargura, 								cc.xy(3,17));
		
		builder.add(buildSubTitleJLabel("Imagens",37),					cc.xy(1,19));
		builder.add(buildElementJLabel("Recupera da pasta do grupo"),	cc.xy(1,21));
		builder.add(iaculaImagemRecuperaGrupo, 							cc.xy(3,21));
		builder.add(buildElementJLabel("Ordenação"), 					cc.xy(5,21));
		builder.add(buildJComboBox(iaculaImagemOrdenacao),				cc.xy(7,21));
		builder.add(buildElementJLabel("Continua da última"), 			cc.xy(1,23));
		builder.add(iaculaImagemContinuaUltima, 						cc.xy(3,23));
		builder.add(buildElementJLabel("Largura máxima"), 				cc.xy(5,23));
		builder.add(iaculaImagemLargura, 								cc.xy(7,23));

		builder.addSeparator("Angelus",									cc.xyw(1,25,7));		
		builder.add(buildElementJLabel("Habilitado"), 					cc.xy(1,27));
		builder.add(angelusHabilitado, 									cc.xy(3,27));
		builder.add(buildElementJLabel("Tempo de exibição"), 			cc.xy(5,27));
		builder.add(angelusTempoExibicao, 								cc.xy(7,27));
		builder.add(buildElementJLabel("Data da Páscoa (dd/mm)"),		cc.xy(1,29));
		builder.add(angelusDataTempoPascal, 							cc.xy(3,29));
		builder.add(buildElementJLabel("Exibir antes"), 				cc.xy(5,29));
		builder.add(angelusExibirComAntecedencia, 						cc.xy(7,29));
		builder.add(buildElementJLabel("Fusio horário"), 				cc.xy(5,31));
		builder.add(buildJComboBox(angelusFusoHorario),					cc.xy(7,31));

		builder.add(buildSubTitleJLabel("Texto",41),					cc.xy(1,33));
		builder.add(buildElementJLabel("Tamanho da fonte"),				cc.xy(1,35));
		builder.add(angelusTextoFonteTamanho, 							cc.xy(3,35));
		builder.add(buildElementJLabel("Fonte"),						cc.xy(5,35));
		builder.add(buildJComboBox(angelusTextoFonteNome),				cc.xy(7,35));
		builder.add(buildElementJLabel("Negrito"), 						cc.xy(1,37));
		builder.add(angelusTextoFonteNegrito, 							cc.xy(3,37));
		builder.add(buildElementJLabel("Itálico"), 						cc.xy(5,37));
		builder.add(angelusTextoFonteItalico, 							cc.xy(7,37));
		builder.add(buildElementJLabel("Largura máxima"), 				cc.xy(1,39));
		builder.add(angelusTextoLargura, 								cc.xy(3,39));

		builder.add(buildSubTitleJLabel("Imagens",37),					cc.xy(1,41));
		builder.add(buildElementJLabel("Altura máxima"), 				cc.xy(1,43));
		builder.add(angelusImagemAltura, 								cc.xy(3,43));
		builder.add(buildElementJLabel("Ordenação"), 					cc.xy(5,43));
		builder.add(buildJComboBox(angelusImagemOrdenacao),				cc.xy(7,43));

		builder.addSeparator("", 										cc.xyw(1,45,7));		
		builder.add(buildJComboBox(fraseButton), 						cc.xy(3,47));
		builder.add(buildJComboBox(textoAngelusButton),					cc.xy(3,49));
		builder.add(buildJComboBox(pastaImagemButton),					cc.xy(5,47));
		builder.add(buildJComboBox(cancelarButton),						cc.xy(5,49));
		builder.add(buildJComboBox(salvarButton), 						cc.xy(7,49));
		
		return builder;
	}

	private JButton buildJComboBox(JButton button){
		button.setFont(new Font("Arial",Font.BOLD,11));
		return button;
	}

	private JComboBox buildJComboBox(JComboBox comboBox){
		comboBox.setFont(new Font("Arial",Font.PLAIN,11));
		return comboBox;
	}

	private JLabel buildElementJLabel(String text){
		return buildCustomJLabel(text, "Arial",Font.PLAIN,11);
	}

	private JLabel buildSubTitleJLabel(String text,int textWidth){
		JLabel subTitle = buildCustomJLabel(text, "Arial",Font.BOLD,11);
		String newText = Utils.insertFragmment(text, " ", textWidth-text.length(), false);
		subTitle.setText(newText);
		return subTitle;
	}

	private JLabel buildCustomJLabel(String text, String fontName, int fontStyle, int fontSize){
		JLabel label = new JLabel(text);
		label.setFont(new Font(fontName,fontStyle,fontSize));
		return label;
	}

	private void salvarAction() {
		ConfigEntity configEntity = new ConfigEntity();

		//IACULA - Geral
		configEntity.setIaculaHabilitado(iaculaHabilitado.isSelected());
		configEntity.setIaculaFrequenciaExibicao(Long.parseLong(iaculaFrequenciaExibicao.getText()));
		configEntity.setIaculaTempoExibicao(Integer.parseInt(iaculaTempoExibicao.getText()));
		
		//IACULA - Frases
		configEntity.setIaculaFraseRandomica(iaculaFraseRandomica.isSelected());
		configEntity.setIaculaFraseGrupoUnico(iaculaFraseUnico.getSelectedItem()+"");
		configEntity.setIaculaFraseGrupoDiaSemana(iaculaFraseDiaSemana.isSelected());
		configEntity.setIaculaFraseContinuaUltima(iaculaFraseContinuaUltima.isSelected());
		configEntity.setIaculaFraseFonteNome(iaculaFraseFonteNome.getSelectedItem()+"");
		configEntity.setIaculaFraseFonteTamanho(Integer.parseInt(iaculaFraseFonteTamanho.getText()));
		configEntity.setIaculaFraseFonteNegrito(iaculaFraseFonteNegrito.isSelected());
		configEntity.setIaculaFraseFonteItalico(iaculaFraseFonteItalico.isSelected());
		configEntity.setIaculaFraseLargura(Integer.parseInt(iaculaFraseLargura.getText()));

		//IACULA - Imagens
		configEntity.setIaculaImagemRecuperaGrupo(iaculaImagemRecuperaGrupo.isSelected());
		configEntity.setIaculaImagemOrdenacao((String)iaculaImagemOrdenacao.getSelectedItem());
		configEntity.setIaculaImagemContinuaUltima(iaculaImagemContinuaUltima.isSelected());
		configEntity.setIaculaImagemLargura(Integer.parseInt(iaculaImagemLargura.getText()));

		//ANGELUS - Geral
		configEntity.setAngelusHabilitado(angelusHabilitado.isSelected());
		configEntity.setAngelusTempoExibicao(Long.parseLong(angelusTempoExibicao.getText()));
		configEntity.setAngelusExibirComAntecedencia(Integer.parseInt(angelusExibirComAntecedencia.getText()));
		configEntity.setAngelusDataTempoPascal(angelusDataTempoPascal.getText());
		configEntity.setAngelusFusoHorario(angelusFusoHorario.getSelectedItem()+"");

		//ANGELUS - Texto
		configEntity.setAngelusTextoFonteNome(angelusTextoFonteNome.getSelectedItem()+"");
		configEntity.setAngelusTextoFonteTamanho(Integer.parseInt(angelusTextoFonteTamanho.getText()));
		configEntity.setAngelusTextoFonteNegrito(angelusTextoFonteNegrito.isSelected());
		configEntity.setAngelusTextoFonteItalico(angelusTextoFonteItalico.isSelected());
		configEntity.setAngelusTextoLargura(Integer.parseInt(angelusTextoLargura.getText()));

		//ANGELUS - Imagens
		configEntity.setAngelusImagemOrdenacao((String)angelusImagemOrdenacao.getSelectedItem());
		configEntity.setAngelusImagemAltura(Integer.parseInt(angelusImagemAltura.getText()));
		
		iaculaController.persistirConfiguracoes(configEntity);
	}
	
	private void carregarIaculaFraseUnicoJComboBox(){
		iaculaFraseUnico.removeAllItems();
		for(String valor : this.getGruposExecucao()){
			iaculaFraseUnico.addItem(valor);
		}
	}

	private void carregaFrameValoresArquivo() {
		ConfigEntity configEntity = iaculaController.getConfigEntity();

		//IACULA - Geral
		iaculaHabilitado.setSelected(configEntity.isIaculaHabilitado());
		iaculaFrequenciaExibicao.setText(String.valueOf(configEntity.getIaculaFrequenciaExibicao()));
		iaculaTempoExibicao.setText(String.valueOf(configEntity.getIaculaTempoExibicao()));

		//IACULA - Frases
		iaculaFraseRandomica.setSelected(configEntity.isIaculaFraseRandomica());
		iaculaFraseUnico.setSelectedItem(configEntity.getIaculaFraseGrupoUnico());
		iaculaFraseDiaSemana.setSelected(configEntity.isIaculaFraseGrupoDiaSemana());
		iaculaFraseContinuaUltima.setSelected(configEntity.isIaculaFraseContinuaUltima());
		iaculaFraseFonteNome.setSelectedItem(configEntity.getIaculaFraseFonteNome());
		iaculaFraseFonteTamanho.setText(String.valueOf(configEntity.getIaculaFraseFonteTamanho()));
		iaculaFraseFonteNegrito.setSelected(configEntity.isIaculaFraseFonteNegrito());
		iaculaFraseFonteItalico.setSelected(configEntity.isIaculaFraseFonteItalico());
		iaculaFraseLargura.setText(String.valueOf(configEntity.getIaculaFraseLargura()));

		//IACULA - Imagens
		iaculaImagemRecuperaGrupo.setSelected(configEntity.isIaculaImagemRecuperaGrupo());
		iaculaImagemOrdenacao.setSelectedItem(configEntity.getIaculaImagemOrdenacao());
		iaculaImagemContinuaUltima.setSelected(configEntity.isIaculaImagemContinuaUltima());
		iaculaImagemLargura.setText(String.valueOf(configEntity.getIaculaImagemLargura()));

		//ANGELUS - Geral
		angelusHabilitado.setSelected(configEntity.isAngelusHabilitado());
		angelusTempoExibicao.setText(String.valueOf(configEntity.getAngelusTempoExibicao()));
		angelusExibirComAntecedencia.setText(String.valueOf(configEntity.getAngelusExibirComAntecedencia()));
		angelusDataTempoPascal.setText(configEntity.getAngelusDataTempoPascal());
		angelusFusoHorario.setSelectedItem(configEntity.getAngelusFusoHorario());

		//ANGELUS - Texto
		angelusTextoFonteNome.setSelectedItem(configEntity.getAngelusTextoFonteNome());
		angelusTextoFonteTamanho.setText(String.valueOf(configEntity.getAngelusTextoFonteTamanho()));
		angelusTextoFonteNegrito.setSelected(configEntity.isAngelusTextoFonteNegrito());
		angelusTextoFonteItalico.setSelected(configEntity.isAngelusTextoFonteItalico());
		angelusTextoLargura.setText(String.valueOf(configEntity.getAngelusTextoLargura()));

		//ANGELUS - Imagens
		angelusImagemOrdenacao.setSelectedItem(configEntity.getAngelusImagemOrdenacao());
		angelusImagemAltura.setText(String.valueOf(configEntity.getAngelusImagemAltura()));
	}

	private void carregaLegendaComponentes() {
		//IACULA - Geral
		iaculaHabilitado.setToolTipText("Habilita o aplicativo para trabalhar com o iacula.");
		iaculaFrequenciaExibicao.setToolTipText("Define de quanto em quanto tempo (em segundos) o iacula será exibido.");
		iaculaTempoExibicao.setToolTipText("Define por quanto tempo (em segundos) o iacula será exibido.");
		
		//IACULA - Frases
		iaculaFraseRandomica.setToolTipText("Define a ordem de exibição de cada frase em cada alerta do iacula. Se desmarcado, a ordem será a do arquivo.");
		iaculaFraseUnico.setToolTipText("Define o nome do grupo de execução.");
		iaculaFraseDiaSemana.setToolTipText("Define se o grupo de execução será por dia da semana. Ex: 1 = Domingo, 2 = Segunda...");
		iaculaFraseContinuaUltima.setToolTipText("Define, ao executar o iacula, se a próxima frase será mostrada em relação a última frase exibida.");
		iaculaFraseFonteNome.setToolTipText("Define a fonte para as frases.");
		iaculaFraseFonteTamanho.setToolTipText("Define o tamanho da fonte.");
		iaculaFraseFonteNegrito.setToolTipText("Define a fonte para negrito.");
		iaculaFraseFonteItalico.setToolTipText("Define a fonte para itálico.");
		iaculaFraseLargura.setToolTipText("Define a largura máxima (em caracter) para as frases no alerta.");
		
		//IACULA - Imagens
		iaculaImagemRecuperaGrupo.setToolTipText("Define se o depósito das imagens será na pasta com o mesmo nome do grupo de execução.");
		iaculaImagemOrdenacao.setToolTipText("Define o modo de ordenação para exibição de cada imagem em cada alerta do iacula.");
		iaculaImagemContinuaUltima.setToolTipText("Define, ao executar o iacula, se a próxima imagem será mostrada em relação a última imagem exibida.");
		iaculaImagemLargura.setToolTipText("Define a largura máxima (em pixel) para as imagens no alerta.");

		//ANGELUS - Geral
		angelusHabilitado.setToolTipText("Habilita o aplicativo para trabalhar com o angelus.");
		angelusTempoExibicao.setToolTipText("Define por quanto tempo (em segundos) o angelus será exibido.");
		angelusExibirComAntecedencia.setToolTipText("Define em quanto tempo antes do meio-dia (em segundos) o angelus será exibido.");
		angelusDataTempoPascal.setToolTipText("Define a data (no formato dd/mm) da Páscoa para o ano corrente.");
		angelusFusoHorario.setToolTipText("Define o fuso horário, pelo qual a aplicação se baseará para exibir o Angelus.");

		//ANGELUS - Texto
		angelusTextoFonteNome.setToolTipText("Define a fonte para o texto.");
		angelusTextoFonteTamanho.setToolTipText("Define o tamanho da fonte.");
		angelusTextoFonteNegrito.setToolTipText("Define a fonte para negrito.");
		angelusTextoFonteItalico.setToolTipText("Define a fonte para itálico.");
		angelusTextoLargura.setToolTipText("Define a largura máxima (em caracter) para o texto no alerta.");

		//ANGELUS - Imagem
		angelusImagemOrdenacao.setToolTipText("Define o modo de ordenação para exibição de cada imagem em cada alerta do angelus.");
		angelusImagemAltura.setToolTipText("Define a altura máxima (em pixel) para as imagens no alerta.");

		//Botões
		salvarButton.setToolTipText("Salva as alterações");
		fraseButton.setToolTipText("Abre o arquivo para edição das frases do Iacula");
		textoAngelusButton.setToolTipText("Abre o arquivo para edição dos textos do Angelus");
		pastaImagemButton.setToolTipText("Abre a pasta de imagens");
		cancelarButton.setToolTipText("Fecha a tela de configurações sem salvar as alterações");
	}
	
	private String[] getFontNames(){
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String[] fontNames = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			fontNames[i] = fonts[i].getName();
		}
		return fontNames;
	}

	private String[] getGruposExecucao(){
		return iaculaController.findGruposExecucao();
	}

	private String[] getFusosHorarios(){
		String[] availableIDs = TimeZone.getAvailableIDs();
		String[] fusosHorarios = new String[availableIDs.length];
		for (int i = 0; i < availableIDs.length; i++) {
			fusosHorarios[i] = availableIDs[i];
		}
		return fusosHorarios;
	}

	private class ConfiguracaoHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				String linhaComando = "notepad " + Config.getCaminhoRecursos();
				if(e.getSource() == salvarButton){
					setVisible(false);
					salvarAction();
					iaculaController.ativarSystemTray();//assim para caso estiver desativado.
					iaculaController.reiniciarAlertas();
				}else if(e.getSource() == pastaImagemButton){
					linhaComando = "explorer "+new File(Config.getCaminhoRecursos()+Config.caminhoDiretorioImagens).getAbsolutePath();
				}else if(e.getSource() == fraseButton){
					linhaComando += Config.nomeArquivoIaculaFrases;
				}else if(e.getSource() == textoAngelusButton){
					linhaComando += Config.nomeArquivoAngelusTextos;
				}else if(e.getSource() == cancelarButton){
					int opt = JOptionPane.showConfirmDialog(null, "Deseja sair sem salvar?", Config.VERSAO, JOptionPane.YES_NO_OPTION);
					if (opt == JOptionPane.OK_OPTION) {
						setVisible(false);
					}
				}
				if(e.getSource() == pastaImagemButton || e.getSource() == fraseButton || e.getSource() == textoAngelusButton)
					Runtime.getRuntime().exec(linhaComando);
			} catch (Exception e1) {
				throw new IaculaRuntimeException(e1.getMessage());
			}
		}
	}
}
