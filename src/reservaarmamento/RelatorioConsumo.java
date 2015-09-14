/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaarmamento;

/**
 *
 * @
 */
public class RelatorioConsumo {

    private String nomePM;
    private String materialRetirado;
    private String materialDevolvido;
    private String dataRetirada;
    private String dataDevolucao;
    private String observacao;

    /**
     * @return the nomePM
     */
    public String getNomePM() {
        return nomePM;
    }

    /**
     * @param nomePM the nomePM to set
     */
    public void setNomePM(String nomePM) {
        this.nomePM = nomePM;
    }

    /**
     * @return the materialRetirado
     */
    public String getMaterialRetirado() {
        return materialRetirado;
    }

    /**
     * @param materialRetirado the materialRetirado to set
     */
    public void setMaterialRetirado(String materialRetirado) {
        this.materialRetirado = materialRetirado;
    }

    /**
     * @return the materialDevolvido
     */
    public String getMaterialDevolvido() {
        return materialDevolvido;
    }

    /**
     * @param materialDevolvido the materialDevolvido to set
     */
    public void setMaterialDevolvido(String materialDevolvido) {
        this.materialDevolvido = materialDevolvido;
    }

    /**
     * @return the dataRetirada
     */
    public String getDataRetirada() {
        return dataRetirada;
    }

    /**
     * @param dataRetirada the dataRetirada to set
     */
    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    /**
     * @return the dataDevolucao
     */
    public String getDataDevolucao() {
        return dataDevolucao;
    }

    /**
     * @param dataDevolucao the dataDevolucao to set
     */
    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
