package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import util.Config;
import util.Utils;
import entity.AutoConfigEntity;
import entity.ConfigEntity;


public class IaculaDAO {

	public List findFrasesIacula(){
		//Recuperamos os elementos filhos (children)   
		List grupos = this.findGruposExecucaoAsElement();   
		Iterator i = grupos.iterator();   
		
		List frasesIacula = new ArrayList();
		//Iteramos com os elementos filhos, e filhos do dos filhos   
		while (i.hasNext()) {   
			Element grupo = (Element) i.next();
			if(grupo.getAttribute("valor").getValue().equals(Config.getIaculaFraseGrupoUnico())){
				List frases = grupo.getChildren();
				for (int j = 0; j < frases.size(); j++) {
					Element frase = (Element) frases.get(j);
					frasesIacula.add(frase.getText());
				}
			}
		}   

		//define a ordenação
		if(Config.getIaculaFraseRandomica()){
			Collections.shuffle(frasesIacula);
		}
		return frasesIacula;
	}

	private List<Element> findGruposExecucaoAsElement(){
		//Aqui você informa o nome do arquivo XML.   
		File f = new File(Config.getCaminhoRecursos() + Config.nomeArquivoIaculaFrases);   

		//Criamos uma classe SAXBuilder que vai processar o XML4   
		SAXBuilder sb = new SAXBuilder();   
  
		//Este documento agora possui toda a estrutura do arquivo.   
		Document d;
		try {
			d = sb.build(f);
		} catch (Exception e) {
			throw new IaculaRuntimeException(e.getMessage());
		}   
  
		//Recuperamos o elemento root   
		Element iacula = d.getRootElement();   
 
		//Recuperamos os elementos filhos (children)   
		List<Element> grupos = iacula.getChildren();   
		return grupos;
	}
	
	public String[] findGruposExecucao(){
		List<Element> gruposExecucaoAsElement = this.findGruposExecucaoAsElement();
		String[] gruposExecucao = new String[gruposExecucaoAsElement.size()];
		for (int i = 0; i < gruposExecucaoAsElement.size(); i++) {
			gruposExecucao[i] = gruposExecucaoAsElement.get(i).getAttributeValue("valor");
		}
		return gruposExecucao;
	}

	public List findImagensIacula(){
		String caminho = Config.getCaminhoRecursos() + Config.caminhoDiretorioIaculaImagens;
		if(Config.getIaculaImagemRecuperaGrupo()) caminho += Config.getIaculaFraseGrupoUnico();

		File imagens = new File(caminho);
		if(!imagens.isDirectory()) return new ArrayList();
		List imagensIacula = new ArrayList();
		for (int i = 0; i < imagens.listFiles().length; i++) {
			File imagem = imagens.listFiles()[i];
			if(imagem.isFile()){
				String nome = imagem.getAbsolutePath().toLowerCase();
				if(nome.endsWith(".gif") || nome.endsWith(".jpg") || nome.endsWith(".png") || nome.endsWith(".jpeg")){
					imagensIacula.add(imagem);
				}
			}
		}

		//define a ordenação
		if(Config.getIaculaImagemOrdenacao().equals(Config.ORDEM_ALEATORIA)){
			Collections.shuffle(imagensIacula);
		}else if(Config.getIaculaImagemOrdenacao().equals(Config.ORDENA_POR_DATA)){
			Utils.ordenaCollection(imagensIacula,"lastModified");
		}else if(Config.getIaculaImagemOrdenacao().equals(Config.ORDENA_POR_NOME)){
			Utils.ordenaCollection(imagensIacula,"getName");
		}
		return imagensIacula;
	}
	
	public void save(AutoConfigEntity autoConfigEntity){
		Properties autoConfig = new Properties();
		autoConfig.setProperty("auto.indexFraseAtual",autoConfigEntity.getIaculaIndexFraseAtual()+"");
		autoConfig.setProperty("auto.indexImagemAtual",autoConfigEntity.getIaculaIndexImagemAtual()+"");
		autoConfig.setProperty("auto.grupoExecucao",autoConfigEntity.getIaculaGrupoExecucao()+"");
		autoConfig.setProperty("auto.recuperaImagemGrupo",autoConfigEntity.isRecuperaImagemGrupo()+"");

		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(new File(Config.getCaminhoRecursos() + Config.nomeArquivoAutoPropriedades));
			autoConfig.store(fileOutputStream,"Não alterar este arquivo");
			fileOutputStream.close();
		} catch (Exception e) {
			new IaculaRuntimeException(e.getMessage());
		}
	}

	public AutoConfigEntity getAutoConfigEntity(){
		Properties autoConfig = new Properties();;
		try {
			autoConfig.load(new FileInputStream(new File(Config.getCaminhoRecursos() + Config.nomeArquivoAutoPropriedades)));
		} catch (Exception e) {
			new IaculaRuntimeException(e.getMessage());
		}
		AutoConfigEntity autoConfigEntity = new AutoConfigEntity();
		autoConfigEntity.setIaculaIndexFraseAtual(Integer.parseInt(autoConfig.getProperty("auto.indexFraseAtual","0")));
		autoConfigEntity.setIaculaIndexImagemAtual(Integer.parseInt(autoConfig.getProperty("auto.indexImagemAtual","0")));
		autoConfigEntity.setIaculaGrupoExecucao(autoConfig.getProperty("auto.grupoExecucao"));
		autoConfigEntity.setRecuperaImagemGrupo(new Boolean(autoConfig.getProperty("auto.recuperaImagemGrupo")).booleanValue());
		
		return autoConfigEntity;
	}

        public void save(ConfigEntity configEntity){
                Properties config = configEntity.getProperties();
    
                FileOutputStream fileOutputStream;
                try {
                        fileOutputStream = new FileOutputStream(new File(Config.getCaminhoRecursos() + Config.nomeArquivoPropriedades));
                        config.store(fileOutputStream,"Não alterar este arquivo");
            			fileOutputStream.close();
                } catch (Exception e) {
                        new IaculaRuntimeException(e.getMessage());
                }
        }
    
        public ConfigEntity getConfigEntity(){
                Properties config = new Properties();;
                try {
                        config.load(new FileInputStream(new File(Config.getCaminhoRecursos() + Config.nomeArquivoPropriedades)));
                } catch (Exception e) {
                        new IaculaRuntimeException(e.getMessage());
                }
                ConfigEntity configEntity = new ConfigEntity(config);
                
                return configEntity;
        }
	public List findImagensAngelus(){
		String caminho = Config.getCaminhoRecursos() + Config.caminhoDiretorioAngelusImagens;

		File imagens = new File(caminho);
		if(!imagens.isDirectory()) return new ArrayList();
		List imagensAngelus = new ArrayList();
		for (int i = 0; i < imagens.listFiles().length; i++) {
			File imagem = imagens.listFiles()[i];
			if(imagem.isFile()){
				String nome = imagem.getAbsolutePath().toLowerCase();
				if(nome.endsWith(".gif") || nome.endsWith(".jpg") || nome.endsWith(".png") || nome.endsWith(".jpeg")){
					imagensAngelus.add(imagem);
				}
			}
		}

		//define a ordenação
		if(Config.getAngelusImagemOrdenacao().equals(Config.ORDEM_ALEATORIA)){
			Collections.shuffle(imagensAngelus);
		}else if(Config.getAngelusImagemOrdenacao().equals(Config.ORDENA_POR_DATA)){
			Utils.ordenaCollection(imagensAngelus,"lastModified");
		}else if(Config.getAngelusImagemOrdenacao().equals(Config.ORDENA_POR_NOME)){
			Utils.ordenaCollection(imagensAngelus,"getName");
		}
		return imagensAngelus;
	}
	
	public String getTextoAngelus(){
		return this.getTextoAngelus(false);
	}

	public String getTextoAngelusTempoPascal(){
		return this.getTextoAngelus(true);
	}

	private String getTextoAngelus(boolean tempoPascal){
		//Aqui você informa o nome do arquivo XML.   
		File f = new File(Config.getCaminhoRecursos() + Config.nomeArquivoAngelusTextos);   

		//Criamos uma classe SAXBuilder que vai processar o XML4   
		SAXBuilder sb = new SAXBuilder();   
  
		//Este documento agora possui toda a estrutura do arquivo.   
		Document d;
		try {
			d = sb.build(f);
		} catch (Exception e) {
			throw new IaculaRuntimeException(e.getMessage());
		}   
  
		//Recuperamos o elemento root   
		Element angelus = d.getRootElement();   
 
		String tagTexto = "";
		if(tempoPascal) tagTexto = "texto-tempo-pascal";
		else tagTexto = "texto";
		
		return angelus.getChild(tagTexto) == null ? "" : angelus.getChild(tagTexto).getText();
	}
}
