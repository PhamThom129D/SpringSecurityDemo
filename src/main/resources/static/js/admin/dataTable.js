$(document).ready(function () {
    var table = $('#userTable').DataTable({
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

    $('div.dataTables_filter input')
        .attr('placeholder', 'Nhập từ khóa tìm kiếm...')
        .addClass('focus:outline-none focus:ring-2 focus:ring-blue-300 bg-opacity-80')
        .css('margin-left', '10px');

    const roleFilterDropdown = $(
        '<label class="ml-4 mr-2 font-medium text-gray-700">Lọc theo vai trò:</label>' +
        '<select id="roleFilter" class="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300">' +
        '<option value="">Tất cả</option>' +
        '<option value="Bác sĩ">Bác sĩ</option>' +
        '<option value="Nhân viên tiếp nhận">Nhân viên tiếp nhận</option>' +
        '<option value="Bệnh nhân">Bệnh nhân</option>' +
        '</select>'
    );

    $('div.dataTables_filter').append(roleFilterDropdown);

    $('#roleFilter').on('change', function () {
        const selectedRole = $(this).val();
        table.column(4).search(selectedRole).draw();
    });
});