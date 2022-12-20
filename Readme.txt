Комментарий ментора №1: в репозитории не должно быть внутренних и сгенерированных
	файлов и директорий - добавь гитигнор и удали лишнее - работать
	в данном репо - новый не создавать!



Комментарий ментора №2: закрывать соединение необходимо в файнали блоке

Как было:
    } catch (SQLException e) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    }

Что сделано:
    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        e.printStackTrace();
    }



Комментарий ментора №3: метод, который просто читает данные из бд не должен проходить в транзакции

"Под транзакцией мы понимаем ряд действий (необязательно в БД), которые воспринимаются системой,
    как единый пакет, т.е. или все действия проходят успешно,
    или все откатываются на исходные позиции."

Что сделано:
Удалены строки:
        connection.commit();
        connection.rollback(); (эта строка была в catch блоке)
Чтобы всё заработало переделал блок под "try с ресурсами"



Комментарий ментора №4: в некоторых методах отсутствует коммит

Строка
        connection.commit();
добавлена во все методы, кроме:
        getAllUsers()
Коммит в этот метод не добавлен для соответствия комментарию №3.



Комментарий ментора №5: использовать полиморфизм, обращаться к классу через интерфейс

Как было:
    UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
Должно быть:
    UserDao userDao = new UserDaoJDBCImpl();
Чтобы это заработало, необходимо было добавить импорт:
    import jm.task.core.jdbc.dao.UserDao;
Также сделал это поле private.

