package com.pokearena.service;

import com.pokearena.exceptions.ErroPokemonNaoEncontrado;
import com.pokearena.model.Pokemon;
import com.pokearena.repository.BancoDeDados;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PokeApiService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    public Pokemon buscarPokemon(String nomeOuId) throws ErroPokemonNaoEncontrado {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(BASE_URL + nomeOuId.toLowerCase());
            
            CloseableHttpResponse response = httpClient.execute(request);
            
            if (response.getCode() != 200) {
                httpClient.close();
                throw new ErroPokemonNaoEncontrado("Pokemon não encontrado: " + nomeOuId);
            }
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent())
            );
            
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            
            httpClient.close();
            
            JSONObject json = new JSONObject(jsonResponse.toString());
            
            //linhas 46 - 62: verificar com calma e ajustar caso necessário, JSON
            int id = json.getInt("id");
            String nome = json.getString("name");
            
            JSONArray types = json.getJSONArray("types");
            String tipo = types.getJSONObject(0)
                .getJSONObject("type")
                .getString("name");
            
            JSONArray stats = json.getJSONArray("stats");
            int hp = 0;
            int ataque = 0;
            int defesa = 0;
            
            for (int i = 0; i < stats.length(); i++) {
                JSONObject stat = stats.getJSONObject(i);
                String statName = stat.getJSONObject("stat").getString("name");
                int baseStat = stat.getInt("base_stat");
                
                if (statName.equals("hp")) {
                    hp = baseStat;
                } else if (statName.equals("attack")) {
                    ataque = baseStat;
                } else if (statName.equals("defense")) {
                    defesa = baseStat;
                }
            }
            
            String sprite = json.getJSONObject("sprites")
                .getJSONObject("other")
                .getJSONObject("official-artwork")
                .getString("front_default");
            
            Pokemon pokemon = new Pokemon(id, nome, ataque, defesa, hp, tipo, sprite);
            
            BancoDeDados.salvarPokemon(pokemon);
            
            return pokemon;
            
        } catch (ErroPokemonNaoEncontrado e) {
            throw e;
        } catch (Exception e) {
            throw new ErroPokemonNaoEncontrado("Erro ao buscar pokemon: " + nomeOuId + " - " + e.getMessage());
        }
    }
}