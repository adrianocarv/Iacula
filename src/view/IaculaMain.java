package view;
import util.Utils;
import controller.IaculaController;

public class IaculaMain {

	public static void main(String[] args) {
		try {
			IaculaController iaculaController = IaculaController.getInstance();
			iaculaController.executar();
		} catch (Exception e) {
			Utils.lancaErroInesperado(e);
		}
	}
}
