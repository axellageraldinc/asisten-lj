# What is this?
Sebuah BOT LINE untuk mengambil update informasi dari website sarjana.jteti.ugm.ac.id/akademik

# Technology
* Java (Spring)
* [BOT-LINE-SDK-JAVA](https://github.com/line/line-bot-sdk-java)
* Postgresql
* [Jsoup](https://jsoup.org) - Untuk scraping elemen HTML dari sebuah website

# Flow program
1. Follower bot yang baru akan dimasukkan userId-nya ke database
2. Daftar userId yang ada di database tadi akan dijadikan userId tujuan untuk BOT ini melakukan multicast message
