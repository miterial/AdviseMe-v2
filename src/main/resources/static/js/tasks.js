$(document).ready(function(){

    /*$(document).on("change", ".post_modal_image", function () {
        console.log($(this).closest(".card-body").find(".post_modal-text"));
        $(this).closest(".card-body").find(".post_modal-text").prop( "disabled", true );
    });*/

    // add task to queue
    $(document).on("click", "#add_task", {accordion: "#accordion",taskType: "#task_types option:selected"}, addTask);

    $(document).on("click", "#add_task_modal",  {accordion: "#accordion_modal", taskType: "#task_types_modal option:selected"}, addTask);

    function addTask(event) {

        let type = $(event.data.taskType).val();
        console.log(event.data.accordion);

        let taskContent;

        let salt = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);

        switch (type) {
            case "1":
                taskContent = " <div class=\"card\" data-type='" + type + "'  data-id='" + salt + "'>\n" +
                    "<div class=\"card-header\" role=\"tab\" id=\"heading_" + salt + "\">\n" +
                    "    <h6 class=\"mb-0\">\n" +
                    "        <a data-toggle=\"collapse\" href=\"#collapse_" + salt + "\" aria-expanded='true' aria-controls='collapse_" + salt + "'>Import Users</a>\n" +
                    "        <button class='btn btn-danger btn-sm task_remove float-right'>X</button>"+
                    "    </h6>\n" +
                    "</div>\n" +
                    "<div id=\"collapse_" + salt +"\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading_" + salt + "\" data-parent=\""+event.data.accordion +"\">\n" +
                    "    <div class=\"card-body\">\n"+
                    "        <div class=\"form-group\">\n" +
                    "            <label for=\"users\">Enter accounts divided by next line in format: email@email.com,password,https://yourblog.tumblr.com,category</label>"+
                    "             <textarea name=\"users\" class=\"form-control users\"></textarea>"+
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</div>";
                break;
            case "2":
                taskContent = " <div class=\"card\" data-type='" + type + "' data-id='" + salt + "'>\n" +
                    "<div class=\"card-header\" role=\"tab\" id=\"heading_" + salt + "\">\n" +
                    "    <h6 class=\"mb-0\">\n" +
                    "        <a data-toggle=\"collapse\" href=\"#collapse_" + salt + "\" aria-expanded='true' aria-controls='collapse_" + salt + "'>Create posts</a>\n" +
                    "        <button class='btn btn-danger btn-sm task_remove float-right'>X</button>"+
                    "    </h6>\n" +
                    "</div>\n" +
                    "<div id=\"collapse_" + salt +"\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading_" + salt + "\" data-parent=\""+event.data.accordion +"\">\n" +
                    "    <div class=\"card-body\">\n" +
                    "<div class=\"form-group\">\n" +
                    "    <input type=\"file\" id=\"post_image\" name=\"image\" class=\"form-control\">\n" +
                    "</div>" +
                    "        <div class=\"form-group\">\n" +
                    "            <label>Enter post title</label>\n" +
                    "            <input type=\"text\" class=\"form-control post-title\">\n" +
                    "        </div>\n" +
                    "        <div class=\"form-group\">\n" +
                    "            <label>Enter post text:</label>\n" +
                    "            <textarea name=\"posts\" class=\"form-control post-text\"></textarea>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</div>";
                break;
            case "3":
                taskContent = " <div class=\"card\" data-type='" + type + "' data-id='" + salt + "'>\n" +
                    "<div class=\"card-header\" role=\"tab\" id=\"heading_" + salt + "\">\n" +
                    "    <h6 class=\"mb-0\">\n" +
                    "        <a data-toggle=\"collapse\" href=\"#collapse_" + salt + "\" aria-expanded='true' aria-controls='collapse_" + salt + "'>Change Blog Info</a>\n" +
                    "        <button class='btn btn-danger btn-sm task_remove float-right'>X</button>"+
                    "    </h6>\n" +
                    "</div>\n" +
                    "<div id=\"collapse_" + salt +"\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading_" + salt + "\" data-parent=\""+event.data.accordion +"\">\n" +
                    "    <div class=\"card-body\">\n" +
                    "<div class=\"form-check\">\n" +
                    "  <input class=\"form-check-input with-link\" type=\"checkbox\" value=\"\">\n" +
                    "  <label class=\"form-check-label\">\n" +
                    "    Include link\n" +
                    "  </label>\n" +
                    "</div>" +
                    "        <div class=\"form-group\">\n" +
                    "            <label>Enter blog title:</label>\n" +
                    "            <input type=\"text\" class=\"form-control\" id=\"blog_modal-title\">\n" +
                    "        </div>\n" +
                    "        <div class=\"form-group\">\n" +
                    "            <label>Enter blog description:</label>\n" +
                    "            <textarea name=\"posts\" id=\"blog_modal-text\" class=\"form-control\"></textarea>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</div>";
                break;
            case "4":
                taskContent = " <div class=\"card\" data-type='" + type + "'  data-id='" + salt + "'>\n" +
                    "<div class=\"card-header\" role=\"tab\" id=\"heading_" + salt + "\">\n" +
                    "    <h6 class=\"mb-0\">\n" +
                    "        <a data-toggle=\"collapse\" href=\"#collapse_" + salt + "\" aria-expanded='true' aria-controls='collapse_" + salt + "'>Delete Last Post</a>\n" +
                    "        <button class='btn btn-danger btn-sm task_remove float-right'>X</button>"+
                    "    </h6>\n" +
                    "</div>\n";
                break;
        }

        $(event.data.accordion).append(taskContent);
    }

    $(document).on("click",".task_remove", function () {
        $(this).closest(".card").remove();
    });


    // Save new queue
    $(".taskqueue_save").on("click", function() {

        let data = {};
        let tasks = [];

        data["category"] = $("#task_categories option:selected").val();

        $(".accordion .card").each(function () {

            let task = {};
            task["type"] = $(this).attr("data-type");

            let id = "#collapse_" + $(this).attr("data-id");

            switch (task["type"]) {
                case "1":
                    task["text"] = $(id).find(".users").val();
                    break;
                case "2":
                    task["title"] = $(id).find(".post-title").val();
                    task["text"] = $(id).find(".post-text").val();

                    let fakePath = $(id + " #post_image").val().split(/[\\]/);
                    task["filePath"] = fakePath[fakePath.length-1];

                    break;
                case "3":
                    task["title"] = $(id).find("#blog_modal-title").val();
                    task["text"] = $(id).find("#blog_modal-text").val();
                    task["withLink"] = $(id).find(".with-link").is(":checked");
                    break;
            }

            tasks.push(task);
        });

        if(tasks.length === 0) {
            errorToast("Add at least one task!");
            return;
        }

        data["tasks"] = tasks;
        //console.log(data);

        $.ajax({
            url: '/tasks/queue',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success:function (response) {
                successToast(response);
                //$(".accordion").empty();
                if(!$("#task_queue_table tr").hasClass("selected"))
                    $("#task_queue_table").DataTable().ajax.reload();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast("Error creating task queue: " + XMLHttpRequest.responseText);
            }
        });

    });


    /** TASK QUEUE TABLE ACTIONS */

    // run tasks from queue
    var toRepeat = false;
    $(document).on("click", ".queue_start", function () {
        let id = $(this).closest("tr").attr("data-id");

        $.ajax({
            url: '/tasks/queue/' + id + "/run",
            type: 'POST',
            beforeSend: function () {
                successToast("Started queue");
            },
            success: function () {
                $.ajax({
                    url: '/tasks/queue/' + id + "/runNext",
                    type: 'POST',
                    statusCode: statusCodeResponses,
                    success: function (response) {
                        successToast(response);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        errorToast(XMLHttpRequest.responseText);
                    }
                });
            }
        });
    });
    var statusCodeResponses = {
        204: function (response) {
            successToast(response);
        },
        200: function () {  // start next task
            $.ajax(this);
        }
    };

    // show queue errors
    $(document).on("click", ".queue_errors", function () {
        let id = $(this).closest("tr").attr("data-id");

        $.ajax({
            url: '/tasks/queue/' + id + "/errors",
            type: 'GET',
            beforeSend: function () {
                console.log("errors for queue " + id);
            },
            success: function (response) {
                $("#errors_modal .modal-body").html(response);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
            }
        });
    });

    $('#errors_modal').on('hidden.bs.modal', function (e) {
        $("#errors_modal .modal-body").html("");
    });

    // delete queue
    $(document).on("click", ".queue_delete", function () {
        let id = $(this).closest("tr").attr("data-id");

        $.ajax({
            url: '/tasks/queue/' + id,
            type: 'DELETE',
            success: function (response) {
                successToast("Successfully deleted queue");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
            }
        });
    });

    // view queue
    $(document).on("click", ".queue_view", function (e) {
        e.preventDefault();
        let id = $(this).closest("tr").attr("data-id");

        $.ajax({
            url: '/tasks/queue/' + id,
            type: 'GET',
            success: function (response) {
                console.log("opened modal");
                $("#queue_modal .modal-body").html(response);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast(XMLHttpRequest.responseText);
            }
        });
    });
    $('#queue_modal').on('hidden.bs.modal', function (e) {
        $("#queue_modal .modal-body").html("");
    });

    //update queue
    $("#queue_modal .btn-submit").on("click", function(e) {
       e.preventDefault();

        let data = {};
        let tasks = [];

        data["category"] = $("#task_categories_modal option:selected").val();

        $("#queue_modal .accordion .card").each(function () {

            let task = {};
            task["type"] = $(this).attr("data-type");

            let id = "#collapse_" + $(this).attr("data-id");
            console.log(id);

            switch (task["type"]) {
                case "1":
                    task["text"] = $(id).find(".users").val();
                    break;
                case "2":
                    task["title"] = $(id).find(".post-title").val();
                    task["text"] = $(id).find(".post-text").val();

                    let fakePath = $(id).find("#post_image").val();
                    if(fakePath === undefined || fakePath === "") {
                        fakePath = $(id).find("#post_image_existing").text();
                    }
                    if (fakePath !== undefined && fakePath !== "") {
                        fakePath = fakePath.split(/[\\]/);
                        task["filePath"] = fakePath[fakePath.length - 1];
                    }
                    break;
                case "3":
                    task["title"] = $(id).find("#blog_modal-title").val();
                    task["text"] = $(id).find("#blog_modal-text").val();
                    task["withLink"] = $(id).find(".with-link").is(":checked");
                    break;
            }

            console.log(task);
            tasks.push(task);
        });

        if(tasks.length === 0) {
            errorToast("Add at least one task!");
            return;
        }

        data["tasks"] = tasks;
        data["id"] = $("#queue_modal .accordion").attr("data-id");
        console.log(data);

        let queueId = $("#accordion_modal").attr("data-id");

        $.ajax({
            url: '/tasks/queue',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success:function (response) {
                successToast(response);
                //$(".accordion").empty();
                if(!$("#task_queue_table tr").hasClass("selected"))
                    $("#task_queue_table").DataTable().ajax.reload();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                errorToast("Error creating task queue: " + XMLHttpRequest.responseText);
            }
        });
    });



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