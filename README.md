# What is this?
Sebuah BOT LINE untuk gap ku terkasih LJ. Fungsi BOT ini adalah untuk mencatat tugas-tugas kuliah dan serba-serbi ujian di kampus sehingga SEMUA ORANG bisa menjadi mahasiswa yang lebih baik lagi.

# Technology
* Java (Spring)
* [BOT-LINE-SDK-JAVA](https://github.com/line/line-bot-sdk-java)
* Postgresql
* [Jsoup](https://jsoup.org) - Untuk scraping elemen HTML dari sebuah website

# Flow program
1. Follower bot yang baru akan dimasukkan userId-nya ke database
2. Daftar userId yang ada di database tadi akan dijadikan userId tujuan untuk BOT ini melakukan multicast message
