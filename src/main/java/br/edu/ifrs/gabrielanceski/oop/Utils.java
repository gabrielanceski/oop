package br.edu.ifrs.gabrielanceski.oop;

import br.edu.ifrs.gabrielanceski.oop.model.User;

public class Utils {

    /**
     * Método temporário enquanto o sistema de usuários não for desenvolvido.
     * @return Usuário temporário de teste.
     */
    public static User dummyUser() {
        User user = new User();
        user.setId(1);
        user.setNickname("Teste");
        user.setEmail("teste@exemplo.com");
        user.setPassword("teste");
        return user;
    }

}
