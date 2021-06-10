package com.example.myapplication

class DataInformation() {
    private var menuList = arrayListOf(
        "Ayam Goreng\n\nLorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                " Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies," +
                " purus lectus malesuada libero, sit amet commodo magna eros quis urna.",
        "Rendang Sapi\n\nLorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                " Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies," +
                " purus lectus malesuada libero, sit amet commodo magna eros quis urna.",
        "Ikan Panggang\n\nLorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                " Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies," +
                " purus lectus malesuada libero, sit amet commodo magna eros quis urna.",
        "Cumi Rebus\n\nLorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                " Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies," +
                " purus lectus malesuada libero, sit amet commodo magna eros quis urna."
    )
    private var index = -1
    fun addMenu (str : String) {
        menuList.add(str)
    }
    fun removeMenu (str : String){
        menuList.remove(str)
    }
    fun backToStart() {
        index = -1
    }
    fun getMenu() : String {
        if(menuList.size == 0) return "Kosong"
        if(index+1 == menuList.size) backToStart()
        index += 1
        return menuList.get(index)
    }
}