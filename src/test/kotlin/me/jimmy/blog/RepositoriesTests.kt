package me.jimmy.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val em: TestEntityManager,
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val jimmy = User("jamesMcGill", "James", "McGill")
        em.persist(jimmy)

        val article = Article("BCS", "BCS", "SG WAS HERE", jimmy)
        em.persist(article)

        em.flush()

        val found = articleRepository.findByIdOrNull(article.id!!)
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val jimmy = User("jamesMcGill", "James", "McGill")
        em.persist(jimmy)

        em.flush()

        val found = userRepository.findByLogin(jimmy.login)
        assertThat(found).isEqualTo(jimmy)
    }

}