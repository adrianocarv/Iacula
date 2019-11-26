package model;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.jdom.Element;

import util.Config;
import util.Utils;
import entity.AutoConfigEntity;
import entity.ConfigEntity;


public class IaculaService {

	private IaculaDAO iaculaDAO = new IaculaDAO();

	public List findIndicesIacula(){
		return new ArrayList();
	}

	public List findFrasesIacula(){
		List frasesIacula = iaculaDAO.findFrasesIacula();
		//ajusta conforme a configuração da largura
		for (int i = 0; i < frasesIacula.size(); i++) {
			String fraseIacula = frasesIacula.get(i)+"";
			frasesIacula.set( i,Utils.getTextoVariasLinhas( fraseIacula, Config.getIaculaFraseLargura()) );
		}
		return frasesIacula;
	}

	public List findImagensIacula() throws Exception{
		//recure uma lista de File
		List imagensIacula = iaculaDAO.findImagensIacula();
		
		//redimensiona cada elemento, convertendo numa lista de ImageIcon
		for (int i = 0; i < imagensIacula.size(); i++) {
			File imagemFile = (File)imagensIacula.get(i);
			ImageIcon imageIcon = this.getImagemIaculaDimensionada(imagemFile.getAbsolutePath());
			imagensIacula.set(i,imageIcon);
		}
		return imagensIacula;
	}

	public void persistirAutoConfigEntity(AutoConfigEntity autoConfigEntity){
		iaculaDAO.save(autoConfigEntity);
	}

    public ConfigEntity getConfigEntity(){
            return iaculaDAO.getConfigEntity();
    }

    public void persistirConfigEntity(ConfigEntity configEntity){
            iaculaDAO.save(configEntity);
    }
    
	public int getAutoIndexFraseAtual(){
		AutoConfigEntity autoConfigEntity = iaculaDAO.getAutoConfigEntity();
		int index = 0;

		//só aproveita o index da execução anterior, se atender as seguintes condições:
		boolean continuaUltima = Config.getIaculaFraseContinuaUltima();
		boolean ordemNaoAleatoria = !Config.getIaculaFraseRandomica();
		boolean mesmoGrupoExecucao = Config.getIaculaFraseGrupoUnico().equals(autoConfigEntity.getIaculaGrupoExecucao());

		if(continuaUltima && ordemNaoAleatoria && mesmoGrupoExecucao){
			//outra condição deve ser atendida (condição aqui dentro para melhorar performance)
			boolean indexAutoMenorIgualLista = autoConfigEntity.getIaculaIndexFraseAtual() <= iaculaDAO.findFrasesIacula().size();
			if(indexAutoMenorIgualLista)
				index = autoConfigEntity.getIaculaIndexFraseAtual();				
		}

		return index;
	}

	public int getAutoIndexImagemAtual() {
		AutoConfigEntity autoConfigEntity = iaculaDAO.getAutoConfigEntity();
		int index = 0;

		//só aproveita o index da execução anterior, se atender as seguintes condições:
		boolean continuaUltima = Config.getIaculaImagemContinuaUltima();
		boolean ordemNaoAleatoria = !Config.getIaculaImagemOrdenacao().equals(Config.ORDEM_ALEATORIA);
		boolean mesmoGrupoExecucao = Config.getIaculaFraseGrupoUnico().equals(autoConfigEntity.getIaculaGrupoExecucao());
		boolean mesmoDiretorioRecuperacaoImagem = !(Config.getIaculaImagemRecuperaGrupo() ^ autoConfigEntity.isRecuperaImagemGrupo());
			
		if (continuaUltima && ordemNaoAleatoria && mesmoGrupoExecucao && mesmoDiretorioRecuperacaoImagem){
			//outra condição deve ser atendida (condição aqui dentro para melhorar performance)
			boolean indexAutoMenorIgualLista = autoConfigEntity.getIaculaIndexImagemAtual() <= iaculaDAO.findImagensIacula().size();
			if(indexAutoMenorIgualLista)
				index = autoConfigEntity.getIaculaIndexImagemAtual();				
		}

		return index;
	}

	public String[] findGruposExecucao(){
		return iaculaDAO.findGruposExecucao();
	}
	
	public List findImagensAngelus() throws Exception{
		//recure uma lista de File
		List imagensAngelus = iaculaDAO.findImagensAngelus();
		
		//redimensiona cada elemento, convertendo numa lista de ImageIcon
		for (int i = 0; i < imagensAngelus.size(); i++) {
			File imagemFile = (File)imagensAngelus.get(i);
			ImageIcon imageIcon = this.getImagemAngelusDimensionada(imagemFile.getAbsolutePath());
			imagensAngelus.set(i,imageIcon);
		}
		return imagensAngelus;
	}

	public String getTextoAngelus(){
		String textoAngelus = "";
		String strDataTempoPascal = Config.getAngelusDataTempoPascal()+"/"+Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);
		try {
			Date dataTempoPascal = format.parse(strDataTempoPascal);
			Calendar pentecostes = Calendar.getInstance();
			pentecostes.setTime(dataTempoPascal);
			pentecostes.add(Calendar.DAY_OF_MONTH,50);

			Date hoje = new Date();
/*
			Calendar calHoje = Calendar.getInstance();
			calHoje.set(2007,3,23);
			Date hoje = calHoje.getTime();
                        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dataTempoPascal);
                        System.out.println("Pascoa: "+data.format(dataTempoPascal)+" - "+cal.get(Calendar.DAY_OF_WEEK));
                        cal.setTime(hoje);
                        System.out.println("Hoje: "+data.format(hoje)+" - "+cal.get(Calendar.DAY_OF_WEEK));
                        cal.setTime(pentecostes.getTime());
                        System.out.println("Pentecostes: "+data.format(pentecostes.getTime())+" - "+cal.get(Calendar.DAY_OF_WEEK));
*/			
			if(hoje.after(dataTempoPascal) && hoje.before(pentecostes.getTime())){
				textoAngelus = iaculaDAO.getTextoAngelusTempoPascal();
			}else{
				textoAngelus = iaculaDAO.getTextoAngelus();
			}
		} catch (ParseException e) {}
		textoAngelus = Utils.getTextoVariasLinhas(textoAngelus,Config.getAngelusTextoLargura());
		return textoAngelus;
	}

	private ImageIcon getImagemIaculaDimensionada(String caminhoOrigem) throws Exception{
		return this.getImagemDimensionada(caminhoOrigem, Config.getIaculaImagemLargura(), true, "imagem_iacula_atual");
	}

	private ImageIcon getImagemAngelusDimensionada(String caminhoOrigem) throws Exception{
		return this.getImagemDimensionada(caminhoOrigem, Config.getAngelusImagemAltura(), false, "imagem_angelus_atual");
	}

	private ImageIcon getImagemDimensionada(String caminhoOrigem, int dimensao, boolean largura, String nomeImagemDimensionada) throws Exception{
		// load image from INFILE
		Image image = Toolkit.getDefaultToolkit().getImage(caminhoOrigem);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);

		// determine thumbnail size from WIDTH and HEIGHT
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		int thumbWidth = -1;
		int thumbHeight = -1;
		if(largura){
			thumbWidth = dimensao;
			thumbHeight = (int) (imageHeight * thumbWidth / imageWidth);
		}else{
			thumbHeight = dimensao;
			thumbWidth = (int) (imageWidth * thumbHeight / imageHeight);
		}
		
		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

		return new ImageIcon(thumbImage);
	}
}
