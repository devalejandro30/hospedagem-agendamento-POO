package com.montecito.projeto.util;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FabricaDeDaos {
    private static final Map<Class<?>, Object> map = new HashMap<>();

    public static <T> T getDao(Class<T> tipo) throws RuntimeException {

        Object obj = map.get(tipo);

        if (obj == null) {
            // Criação do objeto reflections que indica o package onde
            // será procurada a classe que implementa a interface passada
            // para getDao().

            // Reflections reflections = new Reflections(
            // "com.carlosribeiro.projeto.dao.impl");

            Reflections reflections = new Reflections(
                    new ConfigurationBuilder()
                            // CRUCIAL: Substitua abaixo pelo pacote exato da sua matéria/aula
                            .forPackage("com.montecito.projeto.dao.impl")
                            // Informa à biblioteca para buscar apenas subtipos e anotações na memória
                            .setScanners(Scanners.SubTypes, Scanners.TypesAnnotated)
            );

            // Irá retornar um Set contendo o objeto Class referente a
            // EmpregadoDaoImpl.
            Set<Class<? extends T>> conjunto = reflections.getSubTypesOf(tipo);

            // Verfica se existe apenas uma classe que implementa a interface tipo.
            if (conjunto.size() != 1)
                throw new RuntimeException(
                        "Deve haver uma e apenas uma classe que implementa a interface " +
                                tipo.getName());
            // Recupera o objeto class da classe que implementa a interface tipo.
            Class<? extends T> classe = conjunto.iterator().next();

            // Instanciar um objeto do tipo classe.
            try {
                obj = classe.getConstructor().newInstance();
                map.put(tipo, obj);
            } catch (InstantiationException |     // se tentar dar um new em uma interface
                     IllegalAccessException |     // se o construtor de EmpregadoDaoImpl for private
                     InvocationTargetException |  // se durante a execução do construtor ocorrer um erro
                     NoSuchMethodException e) {   // se o construtor padrão não existir
                throw new RuntimeException(e);
            }
        }
        return tipo.cast(obj);
    }
}
