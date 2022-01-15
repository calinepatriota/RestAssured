package models;

public class Product {
  public String nome;
  public  int preco;
  public String descricao;
  public int quantidade;
  public String _id;


    public Product(String nome, int preco, String descricao, int quantidade){
        this.nome=nome;
        this.preco=preco;
        this.descricao=descricao;
        this.quantidade=quantidade;
    }

    public void setProductId(String _id){
        this._id = _id;
    }
}
