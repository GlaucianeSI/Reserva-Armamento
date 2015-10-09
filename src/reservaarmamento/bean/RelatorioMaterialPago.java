package reservaarmamento.bean;

public class RelatorioMaterialPago {

    private String nome;
    private String rg;
    private String numeroMaterial;
    private String materialRetirado;
    private String material;
    private String destino;
    private String dataret;
    private String datadev;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDataret() {
        return dataret;
    }

    public void setDataret(String dataret) {
        this.dataret = dataret;
    }

    public String getDatadev() {
        return datadev;
    }

    public void setDatadev(String datadev) {
        this.datadev = datadev;
    }

    /**
     * @return the numeroMaterial
     */
    public String getNumeroMaterial() {
        return numeroMaterial;
    }

    /**
     * @param numeroMaterial the numeroMaterial to set
     */
    public void setNumeroMaterial(String numeroMaterial) {
        this.numeroMaterial = numeroMaterial;
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

}
