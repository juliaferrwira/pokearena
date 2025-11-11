package com.pokearena.view;

import com.pokearena.repository.BancoDeDados;
import java.sql.Connection;

public class MenuPrincipal {
    public static void main(String[] args) {
        System.out.println("=== POKEARENA ===");
        Connection conn = BancoDeDados.conectar();

        if (conn != null) {
            System.out.println("Conexão com o banco bem-sucedida");
        } else {
            System.out.println("Falha ao conectar ao banco");
        }
    }
}
