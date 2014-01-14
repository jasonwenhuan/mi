/**
 * Created with IntelliJ IDEA.
 * User: Sally
 * Date: 9/7/13
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    init();

    $("#loginButton").click(
        function () {
            checkLoginInfo();
        });

    $("#cancelButton").click(
        function () {
            cancelLoginInfo();
        });
});

function init() {
}

function checkLoginInfo() {
    initLoginArea();
    var userName = $("#username").val();
    var password = $("#password").val();
    if (userName == "" || password == "") {
        if (userName == "") {
            $("#inputUserNameHint").show();
        }
        if (password == "") {
            $("#inputPasswordHint").show();
        }
        return;
    }
    $("#loginForm").submit();
}

function cancelLoginInfo() {
    initLoginArea();
    return;
}

function initLoginArea(){
    $("#username").value = "";
    $("#username").value = "";
    $("#inputUserNameHint").hide();
    $("#inputPasswordHint").hide();
    $("#errorMsg").hide();
}