
$(document).ready(function () {
    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    });

    customizeDropDownMenu();
})

function customizeDropDownMenu() {
    $(".dropdown > a").click(function () {  //access to account details
       location.href = this.href;
    });

    $(".navbar .dropdown").hover(  //hover to account icon
        function () {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function () {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    );
}





