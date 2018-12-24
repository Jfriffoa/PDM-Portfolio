package cl.pdm.pencho.gamecharacterlist

class Personaje{
    
    val image:Int
    val name:String
    val description:String
    val maxHP:Int

    var hp:Int
    
    constructor(name:String, description:String, hp:Int, image:Int){
        this.name = name
        this.description = description
        this.maxHP = hp
        this.hp = hp

        this.image = image
    }

    val HP get() = "Hp Actual: $hp"

    fun reset() {
        hp = maxHP
    }

    fun inDanger() : Boolean {
        return (hp * 1.0f / maxHP) <= .3f
    }
}