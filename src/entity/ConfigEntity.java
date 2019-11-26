package entity;

import java.util.Properties;

import util.Config;


public class ConfigEntity {
    //IACULA - Geral
    private boolean iaculaHabilitado;
    private long iaculaFrequenciaExibicao;
    private long iaculaTempoExibicao;

     //IACULA - Frases
    private boolean iaculaFraseRandomica;
    private String iaculaFraseGrupoUnico;
    private boolean iaculaFraseGrupoDiaSemana;
    private boolean iaculaFraseContinuaUltima;
    private String iaculaFraseFonteNome;
    private int iaculaFraseFonteTamanho;
    private boolean iaculaFraseFonteNegrito;
    private boolean iaculaFraseFonteItalico;
    private int iaculaFraseLargura;
     
     //IACULA - Imagens
    private boolean iaculaImagemRecuperaGrupo;
    private String iaculaImagemOrdenacao;
    private boolean iaculaImagemContinuaUltima;
    private int iaculaImagemLargura;
     
     //Angelus
    private boolean angelusHabilitado;
    private long angelusTempoExibicao;
    private String angelusDataTempoPascal;
    private int angelusExibirComAntecedencia;
    private String angelusFusoHorario;
    private String angelusTextoFonteNome;
    private int angelusTextoFonteTamanho;
    private boolean angelusTextoFonteNegrito;
    private boolean angelusTextoFonteItalico;
    private int angelusTextoLargura;
    private String angelusImagemOrdenacao;
    private int angelusImagemAltura;

    public ConfigEntity(){}
    public ConfigEntity(Properties config){
        this.setIaculaHabilitado(new Boolean(config.getProperty("iacula.habilitado")).booleanValue());
        this.setIaculaFrequenciaExibicao(new Long(config.getProperty("iacula.frequenciaExibicao")).longValue());
        this.setIaculaTempoExibicao(new Long(config.getProperty("iacula.tempoExibicao")).longValue());
        this.setIaculaFraseRandomica(new Boolean(config.getProperty("iacula.frase.randomica")).booleanValue());
        this.setIaculaFraseGrupoUnico(config.getProperty("iacula.frase.grupo.unico"));
        this.setIaculaFraseGrupoDiaSemana(new Boolean(config.getProperty("iacula.frase.grupo.diaSemana")).booleanValue());
        this.setIaculaFraseContinuaUltima(new Boolean(config.getProperty("iacula.frase.continuaUltima")).booleanValue());
        this.setIaculaFraseFonteNome(config.getProperty("iacula.frase.fonte.nome"));
        this.setIaculaFraseFonteTamanho(new Integer(config.getProperty("iacula.frase.fonte.tamanho")).intValue());
        this.setIaculaFraseFonteNegrito(new Boolean(config.getProperty("iacula.frase.fonte.negrito")).booleanValue());
        this.setIaculaFraseFonteItalico(new Boolean(config.getProperty("iacula.frase.fonte.italico")).booleanValue());
        this.setIaculaFraseLargura(new Integer(config.getProperty("iacula.frase.largura")).intValue());
        this.setIaculaImagemRecuperaGrupo(new Boolean(config.getProperty("iacula.imagem.recuperaGrupo")).booleanValue());
        this.setIaculaImagemOrdenacao(config.getProperty("iacula.imagem.ordenacao"));
        this.setIaculaImagemContinuaUltima(new Boolean(config.getProperty("iacula.imagem.continuaUltima")).booleanValue());
        this.setIaculaImagemLargura(new Integer(config.getProperty("iacula.imagem.largura")).intValue());
        this.setAngelusHabilitado(new Boolean(config.getProperty("angelus.habilitado")).booleanValue());
        this.setAngelusTempoExibicao(new Long(config.getProperty("angelus.tempoExibicao")).intValue());
        this.setAngelusDataTempoPascal(config.getProperty("angelus.dataTempoPascal"));
        this.setAngelusExibirComAntecedencia(new Integer(config.getProperty("angelus.exibirComAntecedencia")).intValue());
        this.setAngelusFusoHorario(config.getProperty("angelus.fusoHorario",Config.FUSO_HORARIO_PADRAO));
        this.setAngelusTextoFonteNome(config.getProperty("angelus.texto.fonte.nome"));
        this.setAngelusTextoFonteTamanho(new Integer(config.getProperty("angelus.texto.fonte.tamanho")).intValue());
        this.setAngelusTextoFonteNegrito(new Boolean(config.getProperty("angelus.texto.fonte.negrito")).booleanValue());
        this.setAngelusTextoFonteItalico(new Boolean(config.getProperty("angelus.texto.fonte.italico")).booleanValue());
        this.setAngelusTextoLargura(new Integer(config.getProperty("angelus.texto.largura")).intValue());
        this.setAngelusImagemOrdenacao(config.getProperty("angelus.imagem.ordenacao"));
        this.setAngelusImagemAltura(new Integer(config.getProperty("angelus.imagem.altura")).intValue());
    }

    public Properties getProperties(){
        Properties config = new Properties();
        config.setProperty("iacula.habilitado",this.isIaculaHabilitado()+"");
        config.setProperty("iacula.frequenciaExibicao",this.getIaculaFrequenciaExibicao()+"");
        config.setProperty("iacula.tempoExibicao",this.getIaculaTempoExibicao()+"");
        config.setProperty("iacula.frase.randomica",this.isIaculaFraseRandomica()+"");
        config.setProperty("iacula.frase.grupo.unico",this.getIaculaFraseGrupoUnico()+"");
        config.setProperty("iacula.frase.grupo.diaSemana",this.isIaculaFraseGrupoDiaSemana()+"");
        config.setProperty("iacula.frase.continuaUltima",this.isIaculaFraseContinuaUltima()+"");
        config.setProperty("iacula.frase.fonte.nome",this.getIaculaFraseFonteNome()+"");
        config.setProperty("iacula.frase.fonte.tamanho",this.getIaculaFraseFonteTamanho()+"");
        config.setProperty("iacula.frase.fonte.negrito",this.isIaculaFraseFonteNegrito()+"");
        config.setProperty("iacula.frase.fonte.italico",this.isIaculaFraseFonteItalico()+"");
        config.setProperty("iacula.frase.largura",this.getIaculaFraseLargura()+"");
        config.setProperty("iacula.imagem.recuperaGrupo",this.isIaculaImagemRecuperaGrupo()+"");
        config.setProperty("iacula.imagem.ordenacao",this.getIaculaImagemOrdenacao()+"");
        config.setProperty("iacula.imagem.continuaUltima",this.isIaculaImagemContinuaUltima()+"");
        config.setProperty("iacula.imagem.largura",this.getIaculaImagemLargura()+"");
        config.setProperty("angelus.habilitado",this.isAngelusHabilitado()+"");
        config.setProperty("angelus.tempoExibicao",this.getAngelusTempoExibicao()+"");
        config.setProperty("angelus.dataTempoPascal",this.getAngelusDataTempoPascal()+"");
        config.setProperty("angelus.exibirComAntecedencia",this.getAngelusExibirComAntecedencia()+"");
        config.setProperty("angelus.fusoHorario",this.getAngelusFusoHorario()+"");
        config.setProperty("angelus.texto.fonte.nome",this.getAngelusTextoFonteNome()+"");
        config.setProperty("angelus.texto.fonte.tamanho",this.getAngelusTextoFonteTamanho()+"");
        config.setProperty("angelus.texto.fonte.negrito",this.isAngelusTextoFonteNegrito()+"");
        config.setProperty("angelus.texto.fonte.italico",this.isAngelusTextoFonteItalico()+"");
        config.setProperty("angelus.texto.largura",this.getAngelusTextoLargura()+"");
        config.setProperty("angelus.imagem.ordenacao",this.getAngelusImagemOrdenacao()+"");
        config.setProperty("angelus.imagem.altura",this.getAngelusImagemAltura()+"");

        return config;
    }

    public void setIaculaHabilitado(boolean iaculaHabilitado) {
        this.iaculaHabilitado = iaculaHabilitado;
    }

    public boolean isIaculaHabilitado() {
        return iaculaHabilitado;
    }

    public void setIaculaFrequenciaExibicao(long iaculaFrequenciaExibicao) {
        this.iaculaFrequenciaExibicao = iaculaFrequenciaExibicao;
    }

    public long getIaculaFrequenciaExibicao() {
        return iaculaFrequenciaExibicao;
    }

    public void setIaculaTempoExibicao(long iaculaTempoExibicao) {
        this.iaculaTempoExibicao = iaculaTempoExibicao;
    }

    public long getIaculaTempoExibicao() {
        return iaculaTempoExibicao;
    }

    public void setIaculaFraseRandomica(boolean iaculaFraseRandomica) {
        this.iaculaFraseRandomica = iaculaFraseRandomica;
    }

    public boolean isIaculaFraseRandomica() {
        return iaculaFraseRandomica;
    }

    public void setIaculaFraseGrupoUnico(String iaculaFraseGrupoUnico) {
        this.iaculaFraseGrupoUnico = iaculaFraseGrupoUnico;
    }

    public String getIaculaFraseGrupoUnico() {
        return iaculaFraseGrupoUnico;
    }

    public void setIaculaFraseGrupoDiaSemana(boolean iaculaFraseGrupoDiaSemana) {
        this.iaculaFraseGrupoDiaSemana = iaculaFraseGrupoDiaSemana;
    }

    public boolean isIaculaFraseGrupoDiaSemana() {
        return iaculaFraseGrupoDiaSemana;
    }

    public void setIaculaFraseContinuaUltima(boolean iaculaFraseContinuaUltima) {
        this.iaculaFraseContinuaUltima = iaculaFraseContinuaUltima;
    }

    public boolean isIaculaFraseContinuaUltima() {
        return iaculaFraseContinuaUltima;
    }

    public void setIaculaFraseFonteNome(String iaculaFraseFonteNome) {
        this.iaculaFraseFonteNome = iaculaFraseFonteNome;
    }

    public String getIaculaFraseFonteNome() {
        return iaculaFraseFonteNome;
    }

    public void setIaculaFraseFonteTamanho(int iaculaFraseFonteTamanho) {
        this.iaculaFraseFonteTamanho = iaculaFraseFonteTamanho;
    }

    public int getIaculaFraseFonteTamanho() {
        return iaculaFraseFonteTamanho;
    }

    public void setIaculaFraseFonteNegrito(boolean iaculaFraseFonteNegrito) {
        this.iaculaFraseFonteNegrito = iaculaFraseFonteNegrito;
    }

    public boolean isIaculaFraseFonteNegrito() {
        return iaculaFraseFonteNegrito;
    }

    public void setIaculaFraseFonteItalico(boolean iaculaFraseFonteItalico) {
        this.iaculaFraseFonteItalico = iaculaFraseFonteItalico;
    }

    public boolean isIaculaFraseFonteItalico() {
        return iaculaFraseFonteItalico;
    }

    public void setIaculaFraseLargura(int iaculaFraseLargura) {
        this.iaculaFraseLargura = iaculaFraseLargura;
    }

    public int getIaculaFraseLargura() {
        return iaculaFraseLargura;
    }

    public void setIaculaImagemRecuperaGrupo(boolean iaculaImagemRecuperaGrupo) {
        this.iaculaImagemRecuperaGrupo = iaculaImagemRecuperaGrupo;
    }

    public boolean isIaculaImagemRecuperaGrupo() {
        return iaculaImagemRecuperaGrupo;
    }

    public void setIaculaImagemOrdenacao(String iaculaImagemOrdenacao) {
        this.iaculaImagemOrdenacao = iaculaImagemOrdenacao;
    }

    public String getIaculaImagemOrdenacao() {
        return iaculaImagemOrdenacao;
    }

    public void setIaculaImagemContinuaUltima(boolean iaculaImagemContinuaUltima) {
        this.iaculaImagemContinuaUltima = iaculaImagemContinuaUltima;
    }

    public boolean isIaculaImagemContinuaUltima() {
        return iaculaImagemContinuaUltima;
    }

    public void setIaculaImagemLargura(int iaculaImagemLargura) {
        this.iaculaImagemLargura = iaculaImagemLargura;
    }

    public int getIaculaImagemLargura() {
        return iaculaImagemLargura;
    }

    public void setAngelusHabilitado(boolean angelusHabilitado) {
        this.angelusHabilitado = angelusHabilitado;
    }

    public boolean isAngelusHabilitado() {
        return angelusHabilitado;
    }

    public void setAngelusTempoExibicao(long angelusTempoExibicao) {
        this.angelusTempoExibicao = angelusTempoExibicao;
    }

    public long getAngelusTempoExibicao() {
        return angelusTempoExibicao;
    }

    public void setAngelusDataTempoPascal(String angelusDataTempoPascal) {
        this.angelusDataTempoPascal = angelusDataTempoPascal;
    }

    public String getAngelusDataTempoPascal() {
        return angelusDataTempoPascal;
    }

    public void setAngelusExibirComAntecedencia(int angelusExibirComAntecedencia) {
        this.angelusExibirComAntecedencia = angelusExibirComAntecedencia;
    }

    public int getAngelusExibirComAntecedencia() {
        return angelusExibirComAntecedencia;
    }

    public void setAngelusTextoFonteNome(String angelusTextoFonteNome) {
        this.angelusTextoFonteNome = angelusTextoFonteNome;
    }

    public String getAngelusTextoFonteNome() {
        return angelusTextoFonteNome;
    }

    public void setAngelusTextoFonteTamanho(int angelusTextoFonteTamanho) {
        this.angelusTextoFonteTamanho = angelusTextoFonteTamanho;
    }

    public int getAngelusTextoFonteTamanho() {
        return angelusTextoFonteTamanho;
    }

    public void setAngelusTextoFonteNegrito(boolean angelusTextoFonteNegrito) {
        this.angelusTextoFonteNegrito = angelusTextoFonteNegrito;
    }

    public boolean isAngelusTextoFonteNegrito() {
        return angelusTextoFonteNegrito;
    }

    public void setAngelusTextoFonteItalico(boolean angelusTextoFonteItalico) {
        this.angelusTextoFonteItalico = angelusTextoFonteItalico;
    }

    public boolean isAngelusTextoFonteItalico() {
        return angelusTextoFonteItalico;
    }

    public void setAngelusTextoLargura(int angelusTextoLargura) {
        this.angelusTextoLargura = angelusTextoLargura;
    }

    public int getAngelusTextoLargura() {
        return angelusTextoLargura;
    }

    public void setAngelusImagemOrdenacao(String angelusImagemOrdenacao) {
        this.angelusImagemOrdenacao = angelusImagemOrdenacao;
    }

    public String getAngelusImagemOrdenacao() {
        return angelusImagemOrdenacao;
    }

    public void setAngelusImagemAltura(int angelusImagemAltura) {
        this.angelusImagemAltura = angelusImagemAltura;
    }

    public int getAngelusImagemAltura() {
        return angelusImagemAltura;
    }
	public String getAngelusFusoHorario() {
		return angelusFusoHorario;
	}
	public void setAngelusFusoHorario(String angelusFusoHorario) {
		this.angelusFusoHorario = angelusFusoHorario;
	}
}
