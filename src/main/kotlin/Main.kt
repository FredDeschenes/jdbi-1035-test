import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.h2.H2DatabasePlugin
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.useHandleUnchecked
import org.jdbi.v3.sqlobject.SqlObject
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

fun main(args: Array<String>) {
    val jdbi = Jdbi.create("jdbc:h2:mem:").apply {
        installPlugin(H2DatabasePlugin())
        installPlugin(SqlObjectPlugin())
        installPlugin(KotlinPlugin())
        installPlugin(KotlinSqlObjectPlugin()) // Comment this line to get the error
    }

    jdbi.useHandleUnchecked { handle ->
        val dao = handle.attach(ListUsersDao::class.java)

        dao.createTable()

        dao.createUser("Tester McQa")
        dao.createUser("Hun-it Test")

        println(dao.listUsers())
    }
}

interface ListUsersDao : SqlObject {
    fun createTable() {
        handle.createUpdate("CREATE TABLE users (id INTEGER AUTO_INCREMENT PRIMARY KEY, name TEXT)")
                .execute()
    }

    fun listUsers(): List<User> {
        return handle.createQuery("SELECT * FROM users")
                .mapTo(User::class.java)
                .list()
    }

    fun createUser(name: String) {
        handle.createUpdate("INSERT INTO users(name) VALUES(:name)")
                .bind("name", name)
                .execute()
    }
}

data class User(val id: Int, val name: String)