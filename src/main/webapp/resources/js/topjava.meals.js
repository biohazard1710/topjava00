const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
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
    );
});

function filter() {
    const formData = $('#filterForm').serialize();
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: formData
    }).done(function (data) {
        updateTableByData(data);
        successNoty("Meals filtered");
    }).fail(function (jqXHR) {
        failNoty(jqXHR);
    });
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}
