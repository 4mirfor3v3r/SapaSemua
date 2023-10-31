package amirlabs.sapasemua.data.model

data class QuizResult(
    val _id: String?,
    val creator: String?,
    val module: Module?,
    val quiz: List<Quiz>?,
    val answers: List<String>?,
    val score: Int?,
    val createdAt: String?,
    val updatedAt: String?
)
