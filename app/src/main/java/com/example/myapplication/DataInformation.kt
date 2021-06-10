package com.example.myapplication

class DataInformation() {
    //Data-data string yang disimpan di dalam sebuah array
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
    //fungsi untuk menambahkan pesan
    fun addMenu (str : String) {
        menuList.add(str)
    }
    //fungsi untuk mengahpus pesan
    fun removeMenu (str : String){
        menuList.remove(str)
    }
    //fungsi utk kembali ke proses awal yaitu index -1
    fun backToStart() {
        index = -1
    }
    //fungsi untuk mengambil data string dari array
    fun getMenu() : String {
        //jika size dari array-nya 0 maka akan me-return pesan "kosong"
        if(menuList.size == 0) return "Kosong"
        //jika nilai dari index+1 sama dengan size dari array
        //maka nilai index akan kembali ke awal yaitu -1
        if(index+1 == menuList.size) backToStart()
        index += 1
        return menuList.get(index)
    }
}