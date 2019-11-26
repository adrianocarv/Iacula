package entity;

public class AutoConfigEntity {
	private int iaculaIndexFraseAtual;
	private int iaculaIndexImagemAtual;
	private String iaculaGrupoExecucao;
	private boolean recuperaImagemGrupo;

	public String getIaculaGrupoExecucao() {
		return iaculaGrupoExecucao;
	}
	public void setIaculaGrupoExecucao(String iaculaGrupoExecucao) {
		this.iaculaGrupoExecucao = iaculaGrupoExecucao;
	}
	public int getIaculaIndexFraseAtual() {
		return iaculaIndexFraseAtual;
	}
	public void setIaculaIndexFraseAtual(int iaculaIndexFraseAtual) {
		this.iaculaIndexFraseAtual = iaculaIndexFraseAtual;
	}
	public int getIaculaIndexImagemAtual() {
		return iaculaIndexImagemAtual;
	}
	public void setIaculaIndexImagemAtual(int iaculaIndexImagemAtual) {
		this.iaculaIndexImagemAtual = iaculaIndexImagemAtual;
	}
	public boolean isRecuperaImagemGrupo() {
		return recuperaImagemGrupo;
	}
	public void setRecuperaImagemGrupo(boolean recuperaImagemGrupo) {
		this.recuperaImagemGrupo = recuperaImagemGrupo;
	}
}
