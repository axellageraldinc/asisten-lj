# Contributing
Jika kamu ingin berkontribusi ke repository ini, harap diskusikan terlebih dulu melalui issues/email untuk memberitahu bagian mana yang akan diubah.

# Cara Kontribusi
1. Fork repository ini dengan cara klik "Fork" yang ada di kanan atas.
2. Clone repository yang sudah kamu fork tadi.
3. Lakukan pull request ke branch DEVELOPMENT jika ada perubahan (tentunya setelah didiskusikan terlebih dahulu melalui issues/email)

# Cara menambah fitur
1. Tambahkan interface fitur di service > fitur
2. Tambahkan class implementasi dari interface fitur tadi di service > fitur > implementation
3. Berikan annotation **@Service** pada class implementasi fitur tadi lalu implementasikan interface fitur tadi ke class tsb
4. Setelah selesai membuat fitur, inject **interface** dari fitur itu ke class **EventHandler** yang sesuai yang berada di service > event
5. Pull request ke branch development dengan commit message yang detail
