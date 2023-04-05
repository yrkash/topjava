var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    makeEditable();
});

var filter="";

function getFilter() {
    $.ajax({
        url: ctx.ajaxUrl + "filter/?" + $('#filterForm').serialize(),
        type: "GET"
    }).done(function (data) {
        clearAndAddData(data)
        filter = "filter/?" + $('#filterForm').serialize();
    });
}

function updateTable() {
    $.get(ctx.ajaxUrl + filter, function (data) {
        clearAndAddData(data);
    });
}
