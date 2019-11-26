package controller;

import util.Config;
import util.Utils;

public class IaculaThread extends Thread {

	private IaculaController iaculaController = IaculaController.getInstance();

	public void run(){
		try {
			iaculaController.carregarFrasesIacula();
			iaculaController.carregarImagensIacula();
			iaculaController.carregarIndicesIacula();
			while(true){
				iaculaController.exibirAlertaIacula();
				iaculaController.incrementarIndicesIacula();
				iaculaController.persistirConfiguracoes();
				Thread.sleep(Config.getIaculaTempoExibicao()*1000);
				iaculaController.ocultarAlertaIacula();
				Thread.sleep(Config.getIaculaFrequenciaExibicao()*1000);
			}
		} catch (Exception e) {
			Utils.lancaErroInesperado(e);
		}
	}

	public void parar(){
		iaculaController.ocultarAlertaIacula();
		this.stop();
	}
}
