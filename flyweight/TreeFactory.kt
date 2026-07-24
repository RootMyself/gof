package flyweight

// Flyweight Factory: 같은 조합(name/color/texture)이면 캐싱된 TreeType 재사용
object TreeFactory {
    private val cache = mutableMapOf<String, TreeType>()

    fun getTreeType(name: String, color: String, texture: String): TreeType {
        val key = "$name-$color-$texture"
        return cache.getOrPut(key) {
            println("creating new TreeType: $key")
            TreeType(name, color, texture)
        }
    }

    fun count(): Int = cache.size
}
