/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.EcoPonto;

public class EcoMapsDAO {
    
    Connection conexao = Conexao.getConexao();
    
     public void cadastrarEquipamento(EcoPonto equipamento) throws SQLException {
       // Connection conexao = Conexao.getConexao();

        String sql = "INSERT INTO tbl_equipamentos(nome,descricao,quantidade,marca,sala,estadoConservacao,patrimonio)VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps = conexao.prepareStatement(sql);

        ps.setString(1, equipamento.getNome());
        ps.setString(2, equipamento.getDescricao());
        ps.setString(7,equipamento.getPatrimonio());
    
        ps.execute();
    }
     
 public void pesquisar(EcoPonto equipamento) throws SQLException {

    String sql = "SELECT nome, descricao, quantidade, marca, sala, " +
                 "estadoConservacao, patrimonio " +
                 "FROM tbl_equipamentos WHERE patrimonio = ?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {

        ps.setString(1, equipamento.getPatrimonio().trim());

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                equipamento.setNome(rs.getString("nome"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setPatrimonio(rs.getString("patrimonio"));
            }
        }
    }
}
     
  public void excluir(EcoPonto equipamento) throws SQLException {
   
    String sql = "DELETE FROM tbl_equipamentos WHERE patrimonio = ?";
    try (PreparedStatement ps = conexao.prepareStatement(sql)) {
        ps.setString(1, equipamento.getPatrimonio().trim());
        int linhasAfetadas = ps.executeUpdate();
        System.out.println("Linhas removidas: " + linhasAfetadas);
    }

}

       
    public void alterar(EcoPonto equipamento) throws SQLException {
    String sql = "UPDATE tbl_equipamentos SET nome=?, descricao=?, quantidade=?, marca=?, sala=?, estadoConservacao=? WHERE patrimonio=?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {
        ps.setString(1, equipamento.getNome());
        ps.setString(2, equipamento.getDescricao());
        ps.setString(7, equipamento.getPatrimonio());

        int linhasAfetadas = ps.executeUpdate();
        System.out.println("Linhas atualizadas: " + linhasAfetadas);
    }
}
    
    public List<EcoPonto> pesquisarSala(int sala) throws SQLException {

    List<EcoPonto> equipamentos = new ArrayList<>();

    String sql = "SELECT * FROM tbl_equipamentos WHERE sala=?";

    try (PreparedStatement ps = conexao.prepareStatement(sql)) {

        ps.setInt(1, sala);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                EcoPonto equipamento = new EcoPonto();

                equipamento.setNome(rs.getString("nome"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setPatrimonio(rs.getString("patrimonio"));

                equipamentos.add(equipamento);
            }
        }
    }

    return equipamentos;
}
    
}
