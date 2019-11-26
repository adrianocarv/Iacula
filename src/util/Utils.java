package util;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;


public class Utils {

	public static void ordenaCollection(List list, final String nomeMetodo) {
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				Class classe = o1.getClass();
				try{
					Method method = classe.getMethod(nomeMetodo,null);
					Object object1 = method.invoke(o1,null);
					Object object2 = method.invoke(o2,null);
					if (object1 instanceof Date && object2 instanceof Date)
						return ((Date)object1).compareTo((Date)object2);
					else
						return object1.toString().compareTo(object2.toString());
				}catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
        
    public static String getTextoVariasLinhas(String linha, int larguraLinha){
        if(linha == null || larguraLinha <= 0 || linha.length() <= larguraLinha) return linha;
        int ini = 0;
        int fim = larguraLinha;
        String variasLinhas = "";
        
        while(true){
            if(linha.charAt(fim) != ' '){
            	for(int j=fim; j>ini; j--){
                    if(linha.charAt(j) == ' '){
                        fim = j;
                        break;
                    }
                }
            }
            variasLinhas += linha.substring(ini,fim) + "\n";
            ini = ++fim;
            fim += larguraLinha;
            if(fim >= linha.length()){
            	variasLinhas += linha.substring(ini);
            	break;
            }
        }
        //insere 1 espaço nas extremidades das linhas
        variasLinhas = " "+variasLinhas+" ";
        variasLinhas = variasLinhas.replaceAll("\n"," \n ");
        return variasLinhas;
    }

    public static void lancaErroInesperado(Exception e){
		JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage() + " (" + e.getClass().getName() + ")");
		e.printStackTrace();
		System.exit(0);
    }
    
    public static String getDataHoraFormatada(Date date){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	return dateFormat.format(date);
    }
    
	/**
	 * Insere a quantidade necessária de fragmment a esquerda ou a direita da de source, conforme os parâmetros
	 * @param source
	 * @param fragmment
	 * @param quant
	 * @param left true para inserir a esquerda, false a direita
	 * @return
	 */
	public static String insertFragmment(String source, String fragmment, int quant, boolean left) {
		String changedSource = source;
		for(int i=0; i < quant; i++){
			if (left){
				changedSource = fragmment + changedSource;
			} else{
				changedSource += fragmment;
			}
		}
		return changedSource;
	}
}
