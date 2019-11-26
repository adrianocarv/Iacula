package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Properties;

import model.IaculaRuntimeException;

public class Config {
    public static final String VERSAO = "Iacula 2.9";
    public static final String DATA_VERSAO = "25/05/2007";
    public static final String FUSO_HORARIO_PADRAO = "America/Sao_Paulo";

    private static Properties properties = null;

    private static final String[] caminhos = {"resources/","F:/ac/Projetos/iacula/resources/","D:/ac/Projetos/iacula/resources/","E:/ac/Projetos/iacula/resources/"};

    public static final String caminhoDiretorioImagens = "imagens/";
    public static final String caminhoDiretorioIaculaImagens = caminhoDiretorioImagens+"iacula/";
    public static final String caminhoDiretorioAngelusImagens = caminhoDiretorioImagens+"angelus/";

    public static final String nomeArquivoPropriedades = "config.properties";
    public static final String nomeArquivoAutoPropriedades = "config-auto.properties";
    public static final String nomeArquivoIaculaFrases = "iacula-frases.xml";
    public static final String nomeArquivoAngelusTextos = "angelus-textos.xml";
    public static final String nomeArquivoIconeIacula = "iacula.gif";
    public static final String nomeArquivoIconeIaculaAtivado = "iacula-on.gif";
    public static final String nomeArquivoIconeIaculaDesativado = "iacula-off.gif";

    public static final String ORDENA_POR_NOME = "nome";
    public static final String ORDENA_POR_DATA = "data";
    public static final String ORDEM_ALEATORIA = "randomico";
        
    /**
     * Carrega o arquivo de propriedades
     */
    public static void loadProperties(){
            //if(properties != null) return;
            properties = new Properties();
            try {
                    properties.load(new FileInputStream(new File(getCaminhoRecursos()+nomeArquivoPropriedades)));
            } catch (Exception e) {
                    new IaculaRuntimeException(e.getMessage());
            }
    }

    /**
     * @return
     */
    public static String getCaminhoRecursos(){
            try{
                    File file = null;
                    for (int i = 0; i < caminhos.length; i++) {
                            file = new File(caminhos[i]+nomeArquivoPropriedades);
                            if(file.isFile()) return caminhos[i];
                    }
            }catch (Exception e) {
                new IaculaRuntimeException(e.getMessage());
            }
            return null;
    }

    //IACULA - Geral
    public static final boolean getIaculaHabilitado(){
            String valor = properties.getProperty("iacula.habilitado");
            return new Boolean(valor).booleanValue();
    }
    public static final long getIaculaFrequenciaExibicao(){
            String valor = properties.getProperty("iacula.frequenciaExibicao");
            return Long.parseLong(valor);
    }
    public static final long getIaculaTempoExibicao(){
            String valor = properties.getProperty("iacula.tempoExibicao");
            return Long.parseLong(valor);
    }

    //IACULA - Frases
    public static final boolean getIaculaFraseRandomica(){
            String valor = properties.getProperty("iacula.frase.randomica");
            return new Boolean(valor).booleanValue();
    }
    public static final String getIaculaFraseGrupoUnico(){
            //dá preferência ao dia da semana, se for o caso
            if(getIaculaFraseGrupoDiaSemana()){
                    Calendar calendar = Calendar.getInstance();
                    return calendar.get(Calendar.DAY_OF_WEEK)+"";
            }
            String valor = properties.getProperty("iacula.frase.grupo.unico");
            return valor;
    }
    public static final boolean getIaculaFraseGrupoDiaSemana(){
            String valor = properties.getProperty("iacula.frase.grupo.diaSemana");
            return new Boolean(valor).booleanValue();
    }
    public static final boolean getIaculaFraseContinuaUltima(){
            String valor = properties.getProperty("iacula.frase.continuaUltima");
            return new Boolean(valor).booleanValue();
    }
    public static final String getIaculaFraseFonteNome(){
            String valor = properties.getProperty("iacula.frase.fonte.nome");
            if(valor.equals("")) valor = "Arial";
            return valor;
    }
    public static final int getIaculaFraseFonteTamanho(){
            String valor = properties.getProperty("iacula.frase.fonte.tamanho");
            if(valor.equals("")) valor = "12";
            return Integer.parseInt(valor);
    }
    public static final boolean getIaculaFraseFonteNegrito(){
            String valor = properties.getProperty("iacula.frase.fonte.negrito");
            return new Boolean(valor).booleanValue();
    }
    public static final boolean getIaculaFraseFonteItalico(){
            String valor = properties.getProperty("iacula.frase.fonte.italico");
            return new Boolean(valor).booleanValue();
    }
    public static final int getIaculaFraseLargura(){
            String valor = properties.getProperty("iacula.frase.largura");
            return Integer.parseInt(valor);
    }
    
    //IACULA - Imagens
    public static final boolean getIaculaImagemRecuperaGrupo(){
            String valor = properties.getProperty("iacula.imagem.recuperaGrupo");
            return new Boolean(valor).booleanValue();
    }
    public static final String getIaculaImagemOrdenacao(){
            String valor = properties.getProperty("iacula.imagem.ordenacao");
            return valor;
    }
    public static final boolean getIaculaImagemContinuaUltima(){
            String valor = properties.getProperty("iacula.imagem.continuaUltima");
            return new Boolean(valor).booleanValue();
    }
    public static final int getIaculaImagemLargura(){
            String valor = properties.getProperty("iacula.imagem.largura");
            return Integer.parseInt(valor);
    }
    
    //Angelus
    public static final boolean getAngelusHabilitado(){
            String valor = properties.getProperty("angelus.habilitado");
            return new Boolean(valor).booleanValue();
    }
    public static final long getAngelusTempoExibicao(){
            String valor = properties.getProperty("angelus.tempoExibicao");
            return Long.parseLong(valor);
    }
    public static final String getAngelusDataTempoPascal(){
            String valor = properties.getProperty("angelus.dataTempoPascal");
            return valor;
    }
    public static final int getAngelusExibirComAntecedencia(){
            String valor = properties.getProperty("angelus.exibirComAntecedencia");
            return Integer.parseInt(valor);
    }
    public static final String getAngelusFusoHorario(){
        String valor = properties.getProperty("angelus.fusoHorario",Config.FUSO_HORARIO_PADRAO);
        return valor;
    }
    public static final String getAngelusTextoFonteNome(){
            String valor = properties.getProperty("angelus.texto.fonte.nome");
            if(valor.equals("")) valor = "Arial";
            return valor;
    }
    public static final int getAngelusTextoFonteTamanho(){
            String valor = properties.getProperty("angelus.texto.fonte.tamanho");
            if(valor.equals("")) valor = "12";
            return Integer.parseInt(valor);
    }
    public static final boolean getAngelusTextoFonteNegrito(){
            String valor = properties.getProperty("angelus.texto.fonte.negrito");
            return new Boolean(valor).booleanValue();
    }
    public static final boolean getAngelusTextoFonteItalico(){
            String valor = properties.getProperty("angelus.texto.fonte.italico");
            return new Boolean(valor).booleanValue();
    }
    public static final int getAngelusTextoLargura(){
            String valor = properties.getProperty("angelus.texto.largura");
            return Integer.parseInt(valor);
    }
    public static final String getAngelusImagemOrdenacao(){
            String valor = properties.getProperty("angelus.imagem.ordenacao");
            return valor;
    }
    public static final int getAngelusImagemAltura(){
            String valor = properties.getProperty("angelus.imagem.altura");
            return Integer.parseInt(valor);
    }
}
