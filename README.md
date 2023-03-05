<h1 align="center">Đồ Án Thực Tập - Android<br/>
    Ứng dụng thương mại điện tử nền tảng Android
</h1>

<p align="center">
    <img src="./photo/e-commerce.jpg" width="1280" />
</p>


# [**Table Of Content**](#table-of-content)
- [**Table Of Content**](#table-of-content)
- [**Introduction**](#introduction)
- [**Database**](#database)
- [**Features**](#features)
  - [**1. Login**](#1-login)
  - [**2. Sign Up**](#2-sign-up)
  - [**3. Home**](#3-home)
- [**Video**](#video)
- [**Post Script**](#post-script)
- [Made with 💘 and JAVA ](#made-with--and-java-)

# [**Introduction**](#introduction)

Chào các bạn, mình tên là Nguyễn Thành Phong. Mã sinh viên N18DCCN147, niên khóa 2018-2023. 

Trong mô tả này mình chia sẽ tới các bạn đồ án mà mình thực hiện do nhà trường phân công trong quá trình đi thực tập tại công ty GeoComply. Đồ án này là mô tả chi tiết các chức năng và là một phần bổ trợ cho [**Đồ án thực tập của Phong**](https://github.com/Phong-Kaster/PTIT-Do-An-Thuc-Tap)

Trước khi đi vào các chức năng thì tụi mình cùng nhìn qua về cơ sở dữ liệu nha😆

# [**Database**](#database)

<p align="center">
    <img src="./photo/database.png" />
</p>
<h3 align="center">

***Bản thiết kế tiêu chuẩn cơ sở dữ liệu cho đồ án thực tập***
</h3>

Các bạn có thể thấy rằng cơ sở dữ liệu này ở mức rất cơ bản, dường như độ phức tạp chỉ ngang bằng với một môn học 
ở trường là hết cỡ 😂. 

Một lý do nữa, có thể coi là nguyên tắc bất thành văn, đồ án thực tập & sau này là đồ án tốt nghiệp phải được triển khai với mô hình **Restful API**.
Các thầy cô sẽ không đồng thuận để chúng ta làm đồ án theo phương thức truyền thống nữa - tức là viết luôn phần xử lý trực tiếp trong sản phẩm đầu cuối luôn( website, application,..)

Lý do cuối cùng là mình làm đồ án này hoàn toàn từ đầu 😎, nên cơ sở dữ càng đơn giản ( nhưng phải đủ yêu cầu đề tài ) thì chức năng sẽ càng ít.

> Note: Trong quá trình làm đồ án mình có hỏi các bạn học của mình thì nhiều đứa lấy luôn đồ án cũ ra để nộp, hầu hết là lấy đồ án từ môn [**Phát triển phần mềm hướng dịch vụ**](https://github.com/Phong-Kaster/PTIT-Phat-Trien-Phan-Mem-Huong-Dich-Vu-Website) & kèm thêm một số chỉnh sửa. Sau đó thì đem nộp 😜

Ở phần tiếp theo, mình sẽ trình bày với các bạn về những module xử lý quan trọng nhất của đồ án này. Những phần khác mình không đề cập vì đơn giản là chúng chỉ là các chức năng thêm - xóa - sửa đơn thuần và không có thuật toán hay chỗ nào xử lý quá phức tạp & để dễ hình dung cái mindset của Phong. 

# [**Features**](#features)

## [**1. Login**](#1-login)

Đầu tiên, luôn là chức năng bắt buộc phải có đối với mọi ứng dụng thuộc bất kì nền tảng nào. Đó là chức năng đăng nhập để 
ứng dụng nhận diện danh tính người dùng trên không gian mạng.

<p align="center">
    <img src="./photo/photo_01.jpg" height="640" />
</p>
<h3 align="center">

***Hỗ trợ hai lựa chọn đăng nhập🔐***
</h3>

Để đăng nhập, ứng dụng cho người dùng hai sự lựa chọn: 

1. Đăng nhập bằng tài khoản & mật khẩu truyền thống

2. Đăng nhập bằng tài khoản Google

Với chức năng đăng nhập Google, ứng dụng sử dụng Google API để hoạt động và sẽ hiển thị một cửa 
sổ để người dùng lựa chọn tài khoản đăng nhập

<p align="center">
    <img src="./photo/photo_02_01.jpg" height="640" />
</p>
<h3 align="center">

***Lựa chọn tài khoản Google làm tên đăng nhập🧧***
</h3>

Trường hợp người dùng chưa có bất kì tài khoản Google nào trên thiết bị. Ứng dụng sẽ chuyển hướng 
tới website của Google để tiến hành đăng nhập

<p align="center">
    <img src="./photo/photo_02_02.png" height="640" />
</p>
<h3 align="center">

***Tự động chuyển hướng tới website Google để tiến hành đăng nhập***
</h3>

Qúa trình đăng nhập hoàn tất, người dùng sẽ được tự động chuyển tới trang chủ của ứng dụng và một thông 
báo sẽ hiển thị để người dùng nhận biết rằng họ đã đăng nhập thành công với tài khoản Google.

<p align="center">
    <img src="./photo/photo_05.jpg" height="640" />
</p>
<h3 align="center">

***Tự động chuyển hướng tới website Google để tiến hành đăng nhập***
</h3>


## [**2. Sign Up**](#2-sign-up)

Với tùy chọn đăng nhập bằng tài khoản & mật khẩu truyền thống. Sàn thương mại điện tử Wahoo hỗ trợ cho người sử dụng 
đăng kí trực tiếp với các thông tin cơ bản như sau: 

<p align="center">
    <img src="./photo/photo_03.jpg" height="640" />
</p>
<h3 align="center">

***Đăng ký tài khoản dễ dàng ngay trên ứng dụng🔐***
</h3>

## [**3. Home**](#3-home)

# [**Video**](#video)

<div align="center">
    
[![Watch the video](https://i.imgur.com/vKb2F1B.png)](https://www.youtube.com/watch?v=ZtrGcIkKv4U&ab_channel=PhongKaster)

</div>

<h3 align="center">

***Video***
</h3>

# [**Post Script**](#post-script)

 
# [Made with 💘 and JAVA <img src="https://www.vectorlogo.zone/logos/java/java-horizontal.svg" width="60">](#)