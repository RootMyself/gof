package singleton

class King {
    private constructor()

    companion object {
        @Volatile
        private var instance: King? = null

        fun getInstance(): King =
            instance ?: synchronized(this) {
                instance ?: King().also { instance = it }
            }
    }

    fun call() {
        println("나를 불렀는가? 나는 스톰윈드의 국왕이니라.")
    }
}
