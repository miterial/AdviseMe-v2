$(document).ready(function() {

    // toggle chromedriver visual
    $("#enable_visual").on("click", function () {
        $.ajax({
            url: '/visual',
            type: 'PUT',
            success:function (response) {
                successToast(response)
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast("Error toggling visual. See error log for details");
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });
    });

    // submit user import
    $("#importUsers .btn-submit").on("click", function() {
        var data = {};
        var users = [];
        var ee = $("#importUsers #users").val();
        var usersString = ee.split("\n");

        $.each(usersString, function (i, val) {
            var t = val.split(",");
            if(t.length < 4) {
                errorToast("Incorrect number of credentials in line " + (i + 1));
                return;
            }
            var user = {};
            user["username"] = t[0];
            user["password"] = t[1];
            user["blog"] = t[2];
            user["category"] = t[3];

            users.push(user);
        });

        if(users.length !== usersString.length) {
            errorToast("Users won't be imported");
            return;
        }

        data["users"] = users;
        data["type"] = 1;

        $.ajax({
            url: '/users/import',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function () {
                $("#importUsers").modal("toggle");
            },
            success:function (response) {
                reloadCategories();
                $("#user_table").DataTable().ajax.reload(null, false);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast("Error importing users. See error log for details");
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });
    });


    // submit blog info
    $("#changeBlogInfo .btn-submit").on("click", function() {

        var data = {};
        data["title"] = $("#blog-title").val();
        data["text"] = $("#blog-text").val();
        data["category"] = $("#blog-categories option:selected").val();
        data["withLink"] = $("#changeBlogInfo .with-link").is(":checked");
        data["type"] = 3;

        $.ajax({
            url: '/blogs/info',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function () {
                successToast("Started updating blog info");
                $("#changeBlogInfo").modal("toggle");
            },
            success:function (response) {
                $("#user_table").DataTable().ajax.reload(null, false);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast("Error updating blog info. See error log for details");
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });
    });


    // submit create posts
    $("#createPosts .btn-submit").on("click", function(e) {
        var data = {};
        data["title"] = $("#post-title").val();
        data["text"] = $("#post-text").val();
        data["category"] = $("#post-categories option:selected").val();
        data["type"] = 2;

        let fakePath = $("#post_image").val().split(/[\\]/);
        data["filePath"] = fakePath[fakePath.length-1];

        //console.log(data);

        $.ajax({
            url: '/posts',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function () {
                successToast("Started creating posts");
                $("#createPosts").modal("toggle");
            },
            success:function (response) {
                $("#user_table").DataTable().ajax.reload(null, false);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast("Error creating posts. See error log for details");
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });

    });




    /** TABLE ACTIONS */

    // change category
    $("#changeCategory .btn-submit").on("click", function() {
        if($("#new-category").val() === "") {
            errorToast("Category name must not be empty");
            return;
        }

        var data = {};
        var newCategory = $("#new-category").val();

        var ids = getSelectedIds();
        if(ids.length < 1) {
            errorToast("Select at least one blog!");
            return;
        }

        data["newValue"] = newCategory;
        data["items"] = ids;

        $.ajax({
            url: '/users/category',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function () {
                $("#changeCategory").modal("toggle");
            },
            success:function (response) {
                $("#user_table").DataTable().ajax.reload(null, false);
                successToast(response);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });
    });


    //open links
    $("#openBlogs").on("click", function() {
        if($("table .selected").length === 0) {
            errorToast("Select at least one blog!");
            return;
        }
        $("table .selected a").each(function () {
            var url = $(this).attr('href');
            window.open(url, '_blank');
        });

    });


    //delete blogs
    $("#deleteBlogs").on("click", function() {

        var ids = getSelectedIds();
        if(ids.length < 1) {
            errorToast("Select at least one blog!");
            return;
        }

        var data = {};
        data["items"] = ids;

        $.ajax({
            url: '/users',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success:function (response) {
                $("#user_table").DataTable().ajax.reload(null, false);
                successToast(response);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });

    });

    //delete last post
    $("#deleteLastPost").on("click", function() {
        var ids = getSelectedIds();
        if(ids.length < 1) {
            errorToast("Select at least one blog!");
            return;
        }

        var data = {};
        data["items"] = ids;

        $.ajax({
            url: '/posts',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success:function (response) {
                $("#user_table").DataTable().ajax.reload(null, false);
                successToast(response);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });
    });






    /** UTIL FUNCTIONS */

    // get ids from dselected rows
    function getSelectedIds() {
        var res = [];

        var data = $("#user_table").DataTable().rows('.selected').data();

        data.each(function (row) {
            res.push(row["id"]);
        });

        //console.log(res);
        return res;
    }

    function reloadCategories() {
        $.ajax({
            url: '/categories',
            type: 'GET',
            success:function (response) {
                $("#blog-categories").html(response);
                $("#post-categories").html(response);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
                $("#showErrorLog .card").prepend('<p>' + XMLHttpRequest.responseText + '</p>');
            }
        });
    }

    // toasts
    function successToast(text) {
        var options = {
            style: {
                main: {
                    background: "green",
                    color: "white"
                }
            },
            settings: {
                duration: 2000
            }
        };

        iqwerty.toast.Toast(text, options);
    }

    function errorToast(text) {
        var options = {
            style: {
                main: {
                    background: "red",
                    color: "white"
                }
            }
        };

        iqwerty.toast.Toast(text, options);
    }

});