package controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import model.IaculaService;
import util.Config;
import util.Utils;
import view.AngelusFrame;
import view.ConfiguracaoFrame;
import view.IaculaFrame;
import view.PrincipalSystemTray;
import view.SobreFrame;
import entity.AutoConfigEntity;
import entity.ConfigEntity;


public class IaculaController {

	//singleton
	private static IaculaController iaculaController = null;
	private IaculaController(){};
	public static IaculaController getInstance(){
		if(iaculaController == null) iaculaController = new IaculaController();
		return iaculaController;
	}
	
	private IaculaService iaculaService = new IaculaService();

	//controles
	private List iaculaFrases = new ArrayList();
	private int iaculaIndexFrase = 0;
	private List iaculaImagens = new ArrayList();
	private int iaculaIndexImagem = 0;

	private String angelusTexto = "";
	private List angelusImagens = new ArrayList();
	private int angelusIndexImagem = 0;
	
	//frames
	private PrincipalSystemTray principalSystemTray;
	private IaculaFrame iaculaFrame;
	private AngelusFrame angelusFrame;
	private ConfiguracaoFrame configuracaoFrame;
	private SobreFrame sobreFrame;
	
	//threads
	private AngelusThread angelusThread;
	private IaculaThread iaculaThread;
	private AgendarReinicioAlertasTodoDiaTask agendarReinicioAlertasTodoDiaTask;

	public void executar() throws Exception{
		Config.loadProperties();
		
		//instancia os frames
		principalSystemTray = new PrincipalSystemTray();
		iaculaFrame = new IaculaFrame();
		angelusFrame = new AngelusFrame();
		configuracaoFrame = new ConfiguracaoFrame();
		sobreFrame = new SobreFrame();

		this.reiniciarAlertas();
	}

	public void parar() throws Exception{
		if(iaculaThread != null) iaculaThread.parar();
		if(angelusThread != null) angelusThread.parar();
	}

	public void reiniciarAlertas() throws Exception{
		Config.loadProperties();

		//define o TimeZone pelo fuso-horário do Config
		TimeZone.setDefault(TimeZone.getTimeZone(Config.getAngelusFusoHorario()));

		this.executarIacula();
		this.executarAngelus();
		principalSystemTray.montaTrayIcon();
		this.agendarReinicioAlertasTodoDia();
	}

	public void executarIacula() throws Exception{
		if(iaculaThread != null) iaculaThread.parar();
		
		if(!Config.getIaculaHabilitado()) return;

		iaculaThread = new IaculaThread();
		iaculaThread.start();
	}

	public void executarAngelus() throws Exception{
		if(angelusThread != null) angelusThread.parar();

		if(!Config.getAngelusHabilitado()) return;

		angelusThread = new AngelusThread();
		angelusFrame = new AngelusFrame();
		angelusThread.start();
	}

	public void carregarIndicesIacula(){
		this.iaculaIndexFrase = iaculaService.getAutoIndexFraseAtual();
		this.iaculaIndexImagem = iaculaService.getAutoIndexImagemAtual();
	}

	public void carregarFrasesIacula(){
		this.iaculaFrases = iaculaService.findFrasesIacula();
	}

	public void carregarImagensIacula() throws Exception{
		this.iaculaImagens= iaculaService.findImagensIacula();
	}

	public void incrementarIndicesIacula(){
		if(this.iaculaFrases.size()-1 == this.iaculaIndexFrase) this.iaculaIndexFrase = 0;
		else this.iaculaIndexFrase++;

		if(this.iaculaImagens.size()-1 == this.iaculaIndexImagem) this.iaculaIndexImagem = 0;
		else this.iaculaIndexImagem++;
	}

	public void persistirConfiguracoes(){
		AutoConfigEntity autoConfigEntity = new AutoConfigEntity();
		autoConfigEntity.setIaculaGrupoExecucao(Config.getIaculaFraseGrupoUnico());
		autoConfigEntity.setIaculaIndexFraseAtual(this.iaculaIndexFrase);
		autoConfigEntity.setIaculaIndexImagemAtual(this.iaculaIndexImagem);
		autoConfigEntity.setRecuperaImagemGrupo(Config.getIaculaImagemRecuperaGrupo());
		iaculaService.persistirAutoConfigEntity(autoConfigEntity);
	}

	public void exibirAlertaIacula() throws Exception {
		//constrói os componentes
		//imagem
		ImageIcon iaculaImagem = this.iaculaImagens.size() == 0 ? null : (ImageIcon) this.iaculaImagens.get(this.iaculaIndexImagem);

		//frase
		String iaculaFrase = this.iaculaFrases.size() == 0 ? "" : iaculaFrases.get(this.iaculaIndexFrase)+"";
		
		iaculaFrame.atualizaComponentesFrame(iaculaImagem, iaculaFrase);
		iaculaFrame.exibirAlerta();
	}

	public void ocultarAlertaIacula(){
		iaculaFrame.ocultarAlerta();
	}

	public void carregarTextoAngelus(){
		angelusTexto = iaculaService.getTextoAngelus();
	}
	
	public void carregarImagensAngelus() throws Exception{
		this.angelusImagens= iaculaService.findImagensAngelus();
	}

	public void incrementarIndiceAngelus(){
		if(this.angelusImagens.size()-1 == this.angelusIndexImagem) this.angelusIndexImagem = 0;
		else this.angelusIndexImagem++;
	}

	public void decrementarIndiceAngelus(){
		if(this.angelusIndexImagem == 0 ) this.angelusIndexImagem = this.angelusImagens.size()-1;
		else this.angelusIndexImagem--;
	}

	public void exibirAlertaAngelus() throws Exception {
		//constrói os componentes
		//imagem
		ImageIcon angelusImagem = this.angelusImagens.size() == 0 ? null : (ImageIcon) this.angelusImagens.get(this.angelusIndexImagem);

		//texto
		String angelusTexto = this.angelusTexto;
		
		angelusFrame.atualizaComponentesFrame(angelusImagem, angelusTexto);
		angelusFrame.exibirAlerta();
	}

	public void ocultarAlertaAngelus(){
		angelusFrame.ocultarAlerta();
	}

	public void exibirAlertaConfiguracao() throws Exception {
		configuracaoFrame.exibirAlerta();
	}

	public void exibirAlertaSobre() throws Exception {
		sobreFrame.exibirAlerta();
	}

    public ConfigEntity getConfigEntity(){
        return iaculaService.getConfigEntity();
    }

	public void persistirConfiguracoes(ConfigEntity configEntity){
		iaculaService.persistirConfigEntity(configEntity);
	}
	
	public String[] findGruposExecucao(){
		return iaculaService.findGruposExecucao();
	}

	public void ativarSystemTray(){
		principalSystemTray.ativar();
	}

	/**
	 * Executa o método reiniciarAlertas() no fim de cada dia.<br>
	 * Proporcionando o correto funcionamento da aplicação quando virar o dia com o micro ligado.
	 * Recursos beneficiados:<br>
	 * - Agendamento do Angelus;<br>
	 * - Execução do grupo, quando configurado para dia da semana.
	 */
	private void agendarReinicioAlertasTodoDia(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		Timer timer = new Timer();
		
		if(agendarReinicioAlertasTodoDiaTask != null) agendarReinicioAlertasTodoDiaTask.cancel();
		agendarReinicioAlertasTodoDiaTask = new AgendarReinicioAlertasTodoDiaTask();

		timer.schedule(agendarReinicioAlertasTodoDiaTask,c.getTime());
	}
	private class AgendarReinicioAlertasTodoDiaTask extends TimerTask{
		public void run(){
			try{
				Thread.sleep(2000);
				reiniciarAlertas();
			} catch (Exception e) {
				Utils.lancaErroInesperado(e);
			}
		}
	}
}
