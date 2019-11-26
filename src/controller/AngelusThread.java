package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import util.Config;
import util.Utils;

public class AngelusThread extends Thread {

	private IaculaController iaculaController = IaculaController.getInstance();
	private AngelusTask angelusTask;
	
	public void run(){
		try {
			iaculaController.carregarTextoAngelus();
			iaculaController.carregarImagensAngelus();

			Date dataSistema = new Date();

			//data do agendamento
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY,12);
			c.set(Calendar.MINUTE,00);
			c.set(Calendar.SECOND,00);
			c.add(Calendar.SECOND,-Config.getAngelusExibirComAntecedencia());
			Date dataAgendamento = c.getTime();

			//informa as datas
			System.out.println("Agora: "+Utils.getDataHoraFormatada(dataSistema));
			System.out.println("Agenda: "+Utils.getDataHoraFormatada(dataAgendamento));

			angelusTask = new AngelusTask();
			Timer timer = new Timer();
			//só agenda, a hora do sistema for anterior à hora de alerta do Angelus, neste dia.
			if(dataSistema.compareTo(dataAgendamento) <= 0)
				timer.schedule(angelusTask,dataAgendamento);

		} catch (Exception e) {
			Utils.lancaErroInesperado(e);
		}
	}
	
	public void parar() throws Exception{
		iaculaController.ocultarAlertaAngelus();
		angelusTask.cancel();
		this.stop();
	}

	private class AngelusTask extends TimerTask{
		public void run(){
			try {
				iaculaController.exibirAlertaAngelus();
				iaculaController.incrementarIndiceAngelus();
				Thread.sleep(Config.getAngelusTempoExibicao()*1000);
				iaculaController.ocultarAlertaAngelus();
			} catch (Exception e) {
				Utils.lancaErroInesperado(e);
			}
		}
	}
}
