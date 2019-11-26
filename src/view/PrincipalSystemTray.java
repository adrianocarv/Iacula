package view;
import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.IaculaRuntimeException;
import util.Config;
import controller.IaculaController;

public class PrincipalSystemTray {

	//controles
	private IaculaController iaculaController = IaculaController.getInstance();
	private boolean ativo = true;

	//elementos do SystemTray
	private SystemTray tray;
	private TrayIcon trayIcon;
	private PopupMenu popupMenu = new PopupMenu();

	private MenuItem configurarMenuItem = new MenuItem("Configurar...");
	private MenuItem exibirAngelusMenuItem = new MenuItem("Exibir Angelus...");
	private MenuItem exibirIaculaMenuItem = new MenuItem("Exibir Iacula...");
	private MenuItem ativoMenuItem = new MenuItem("Desativar");
	private MenuItem sobreMenuItem = new MenuItem("Sobre...");
	private MenuItem sairMenuItem = new MenuItem("Sair");
	
	public void ativar(){
		ativo = true;
	}

	public PrincipalSystemTray(){
		//Obtem o SystemTray da plataforma
		tray = SystemTray.getSystemTray();
		this.addActionListeners();
	}

	public void montaTrayIcon() throws AWTException{
		this.montaPopupMenu();
		Image imageIcon;
		if(ativo){
			imageIcon = new ImageIcon(Config.getCaminhoRecursos()+Config.nomeArquivoIconeIaculaAtivado).getImage();
		}else{
			imageIcon = new ImageIcon(Config.getCaminhoRecursos()+Config.nomeArquivoIconeIaculaDesativado).getImage();
		}
		tray.remove(trayIcon);

		if(trayIcon == null) trayIcon = new TrayIcon(imageIcon,Config.VERSAO);
		else trayIcon.setImage(imageIcon);

		trayIcon.setPopupMenu(popupMenu);
		tray.add(trayIcon);
	}

	private void montaPopupMenu(){
		popupMenu.removeAll();
		popupMenu.setFont(new Font("Arial",0,11));

		popupMenu.add(configurarMenuItem);
		popupMenu.add(new MenuItem("-"));
		if(ativo){
			popupMenu.add(exibirAngelusMenuItem);
			popupMenu.add(exibirIaculaMenuItem);

			if(!Config.getAngelusHabilitado()) exibirAngelusMenuItem.setEnabled(false);
			else exibirAngelusMenuItem.setEnabled(true);
			if(!Config.getIaculaHabilitado()) exibirIaculaMenuItem.setEnabled(false);
			else exibirIaculaMenuItem.setEnabled(true);
			
			popupMenu.add(new MenuItem("-"));
			ativoMenuItem.setLabel("Desativar");
		}else{
			ativoMenuItem.setLabel("Ativar");
		}
		popupMenu.add(ativoMenuItem);
		popupMenu.add(new MenuItem("-"));
		popupMenu.add(sobreMenuItem);
		popupMenu.add(sairMenuItem);
	}
	
	private void addActionListeners(){
		addActionConfigurarMenuItem();
		addActionExibirAngelusMenuItem();
		addActionExibirIaculaMenuItem();
		addActionAtivoMenuItem();
		addActionSobreMenuItem();
		addActionSairMenuItem();
	}

	private void addActionConfigurarMenuItem(){
		configurarMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iaculaController.exibirAlertaConfiguracao();
				} catch (Exception e1) {
					throw new IaculaRuntimeException(e1.getMessage());
				}
			}});
	}

	private void addActionExibirAngelusMenuItem(){
		exibirAngelusMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iaculaController.exibirAlertaAngelus();
				} catch (Exception e1) {
					throw new IaculaRuntimeException(e1.getMessage());
				}
			}});
	}

	private void addActionExibirIaculaMenuItem(){
		exibirIaculaMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iaculaController.exibirAlertaIacula();
				} catch (Exception e1) {
					throw new IaculaRuntimeException(e1.getMessage());
				}
			}});
	}

	private void addActionAtivoMenuItem(){
		ativoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(ativo){
						iaculaController.parar();
					}else{
						iaculaController.reiniciarAlertas();
					}
					ativo = !ativo;
					montaTrayIcon();
				} catch (Exception e1) {
					throw new IaculaRuntimeException(e1.getMessage());
				}
			}});
	}

	private void addActionSobreMenuItem(){
		sobreMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iaculaController.exibirAlertaSobre();
				} catch (Exception e1) {
					throw new IaculaRuntimeException(e1.getMessage());
				}
			}});
	}

	private void addActionSairMenuItem(){
		sairMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", Config.VERSAO, JOptionPane.YES_NO_OPTION);
				if (opt == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}});
	}

}
