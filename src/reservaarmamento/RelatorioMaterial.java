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
public class RelatorioMaterial {

    private String nome;
    private int quantidadematerial;
    private int quantidadeexistente;
    private int quantidadeextraviado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the quantidadematerial
     */
    public int getQuantidadematerial() {
        return quantidadematerial;
    }

    /**
     * @param quantidadematerial the quantidadematerial to set
     */
    public void setQuantidadematerial(int quantidadematerial) {
        this.quantidadematerial = quantidadematerial;
    }

    /**
     * @return the quantidadeexistente
     */
    public int getQuantidadeexistente() {
        return quantidadeexistente;
    }

    /**
     * @param quantidadeexistente the quantidadeexistente to set
     */
    public void setQuantidadeexistente(int quantidadeexistente) {
        this.quantidadeexistente = quantidadeexistente;
    }

    /**
     * @return the quantidadeextraviado
     */
    public int getQuantidadeextraviado() {
        return quantidadeextraviado;
    }

    /**
     * @param quantidadeextraviado the quantidadeextraviado to set
     */
    public void setQuantidadeextraviado(int quantidadeextraviado) {
        this.quantidadeextraviado = quantidadeextraviado;
    }
    
}
