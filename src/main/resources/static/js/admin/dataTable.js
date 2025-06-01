// Cấu hình chung cho tất cả các bảng
function initCommonDataTable(selector) {
    return $(selector).DataTable({
        searching: true,
        paging: true,
        lengthChange: true,
        ordering: true,
        info: true,
        autoWidth: true,
        language: {
            "search": "Tìm kiếm  :  ",
            "lengthMenu": "Hiển thị _MENU_ dòng mỗi trang",
            "zeroRecords": "Không tìm thấy kết quả nào",
            "info": "Hiển thị _START_ đến _END_ của _TOTAL_ mục",
            "infoEmpty": "Không có dữ liệu",
            "infoFiltered": "(lọc từ _MAX_ mục)",
            "paginate": {
                "first": "Đầu",
                "last": "Cuối",
                "next": "Sau",
                "previous": "Trước"
            }
        },
        responsive: true
    });
}

$(document).ready(function () {
    // Áp dụng cấu hình DataTable cho tất cả các bảng
    const userTable = initCommonDataTable('#userTable');
    const departmentTable = initCommonDataTable('#departmentTable');
    const invoiceTable = initCommonDataTable('#invoiceTable');

    // Tùy chỉnh ô tìm kiếm cho tất cả bảng
    $('div.dataTables_filter input')
        .attr('placeholder', 'Nhập từ khóa tìm kiếm...')
        .addClass('focus:outline-none focus:ring-2 focus:ring-blue-300 bg-opacity-80')
        .css('margin-left', '10px');

    // 👉 Chỉ thêm filter "Vai trò" cho bảng #userTable
    const roleFilterDropdown = $(
        '<label class="ml-4 mr-2 font-medium text-gray-700">Lọc theo vai trò:</label>' +
        '<select id="roleFilter" class="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300">' +
        '<option value="">Tất cả</option>' +
        '<option value="Bác sĩ">Bác sĩ</option>' +
        '<option value="Nhân viên tiếp nhận">Nhân viên tiếp nhận</option>' +
        '<option value="Bệnh nhân">Bệnh nhân</option>' +
        '</select>'
    );

    // Gắn vào đúng vị trí filter của bảng userTable
    $('#userTable_wrapper div.dataTables_filter').append(roleFilterDropdown);

    // Lọc cột 4 theo vai trò (chỉ cho bảng user)
    $('#roleFilter').on('change', function () {
        const selectedRole = $(this).val();
        userTable.column(4).search(selectedRole).draw();
    });
});
