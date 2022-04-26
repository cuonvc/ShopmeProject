
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

$(document).ready(function () {
    $("#buttonCannel").on("click", function () {
        window.location = "[[@{/users}]]"  //back to the users listing page (ShopmeAdmin/users)
    })

    $("#fileImage").change(function() {
        fileSize = this.files[0].size;

        if (fileSize > 1048576) {  //1MB = 1024 byte = 1024 * 1024 bit
            this.setCustomValidity("You must choose an image less than 1MB!");
            this.reportValidity();
        } else {
            this.setCustomValidity("");

        }
        showImageThumbnail(this);
    })
})

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        $("#thumbnail").attr("src", e.target.result);
    }
    reader.readAsDataURL(file);
}

function checkEmailUnique(form) {
    url = "[[@{/users/check_email}]]";
    userEmail = $("#email").val();
    userId = $("#id").val();
    csrfValue = $("input[name = '_csrf']").val();  //CSRF token Spring security
    params = {id: userId, email: userEmail, _csrf: csrfValue};

    $.post(url, params, function (response) {
        if (response == "OK") {
            form.submit();
        } else if (response == "Duplicated") {
            showModelDialog("Error", "Email này đã được đăng ký trước đó.");
        } else {
            showModelDialog("!", "Server không phản hồi...Ops!");
        }
    }).fail(function () {
        showModelDialog("Ops!", "Server không phản hồi...")
    })

    return false;
}

function showModelDialog(title, message) {
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

$(document).ready(function() {
    $(".link-delete").on("click", function(e) {
        e.preventDefault();
        link = $(this);
        userEmail = link.attr("userEmail");
        $("#yesBtn").attr("href", link.attr("href"));
        $("#confirmText").text("Do you want to delete user: " + userEmail);
        $("#confirmModal").modal();
    })
})

function clearFilter() {
    window.location = "/ShopmeAdmin/users";
}