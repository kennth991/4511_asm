/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function displayEquipment(equipmentId, name, location, description, status, category, imgSrc, isStaff) {
    console.log("Displaying equipment details:", equipmentId);
    document.getElementById("editid").value = equipmentId;
    document.getElementById("editname").value = name;
    document.getElementById("editlocation").value = location;
    document.getElementById("editdescription").value = description;
    document.getElementById("editstatus").value = status;
    document.getElementById("editcategory").value = category;

    var dropdown = document.getElementById("editIsStaffDropdown");
    if (isStaff === "staff") {
        dropdown.value = "staff";
    } else {
        dropdown.value = "user";
    }
}

function deleteEquipment(equipmentId, name, location, description, status, category, imgSrc) {
    console.log("Preparing to delete equipment:", equipmentId);
    document.getElementById("id").value = equipmentId;
    document.getElementById("name").value = name;
    document.getElementById("location").value = location;
    document.getElementById("description").value = description;
    document.getElementById("status").value = status;
}

function damageReport(equipmentId, name, location, description, status, category, imgSrc) {
    console.log("Reporting damage for equipment:", equipmentId);
    document.getElementById("reportid").value = equipmentId;
    document.getElementById("reportname").value = name;
    document.getElementById("reportlocation").value = location;
    document.getElementById("reportdescription").value = description;
    document.getElementById("reportstatus").value = status;
}

function toggleView(selectedView) {
    console.log("Toggling view to:", selectedView);
    if (selectedView === "equipmentList") {
        document.getElementById("equipmentListView").style.display = "block";
        document.getElementById("returnsManagementView").style.display = "none";
    } else if (selectedView === "returnsManagement") {
        document.getElementById("equipmentListView").style.display = "none";
        document.getElementById("returnsManagementView").style.display = "block";
        loadReturnsData(); // Automatically load return data when this view is selected
    }
}
function loadEquipmentDetails(requestId) {
    $.ajax({
        url: baseUrl + '/fetchEquipmentDetails',
        type: 'GET',
        data: {requestId: requestId},
        success: function (data) {
            var equipments = JSON.parse(data);
            var tbody = $('#equipmentDetailsTable tbody');
            tbody.empty();
            equipments.forEach(function (equipment) {
                tbody.append(`<tr>
                    <td>${equipment.equipmentID}</td>
                    <td>${equipment.name}</td>
                    <td>${equipment.location}</td>
                    <td>${equipment.status}</td>
                    <td>
                        <select class="form-select">
                            <option value="available">Available</option>
                            <option value="damaged">Damaged</option>
                        </select>
                        <button class="btn btn-primary" onclick="submitDamageReport(${equipment.equipmentID})">Submit Report</button>
                    </td>
                </tr>`);
            });
        },
        error: function (xhr, status, error) {
            console.error('Failed to load equipment details:', error);
            console.error('Status:', status);
            console.error('Error:', xhr.responseText); // Output full error message/response from server
            alert('Failed to load equipment details: ' + xhr.responseText);
        }
    });
}

function submitDamageReport(equipmentID) {
    var status = $(`#statusSelect${equipmentID}`).val();
    var description = $(`#damageDescription${equipmentID}`).val();

    $.ajax({
        url: '/submitDamageReport',
        type: 'POST',
        data: {
            equipmentID: equipmentID,
            status: status,
            description: description
        },
        success: function (response) {
            alert('Damage report submitted successfully.');
        },
        error: function () {
            alert('Failed to submit damage report.');
        }
    });
}
function loadReturnsData() {
    var url = baseUrl + '/fetchEquipmentDetails';
    console.log("Request URL:", url); // Confirm the request URL

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var tbody = $('#returnsManagementView table tbody');
            tbody.empty();

            if (data.requests && data.requests.length > 0) {
                data.requests.forEach(function (request) {
                    var rows = request.equipments.map((equipment, index) => `
                        <tr>
                            ${index === 0 ? `<td rowspan="${request.equipments.length}">${request.requestID}</td>
                            <td rowspan="${request.equipments.length}">${request.requesterName}</td>
                            <td rowspan="${request.equipments.length}">${request.requestDateTime}</td>
                            <td rowspan="${request.equipments.length}">${request.status}</td>` : ''}
                            <td>${equipment.name}</td>
                            <td>${equipment.location}</td>
                            ${index === 0 ? `<td rowspan="${request.equipments.length}">
                                <button class="btn btn-primary" onclick="manageReturn(${request.requestID})">Manage Return</button>
                            </td>` : ''}
                        </tr>`).join('');

                    tbody.append(rows);
                });
            } else {
                tbody.append('<tr><td colspan="7">No return requests available.</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Failed to load return data:", error);
            $('#returnsManagementView table tbody').append('<tr><td colspan="7">Error loading data.</td></tr>');
        }
    });
}

function manageReturn(requestID) {
    console.log("Managing return for Request ID:", requestID);

    // 弹出模态框让用户检查每件设备是否损坏，并填写损坏报告
    // 假设每个设备的状态和描述都已经在表单中
    if (confirm('Please confirm management of the return. Check if any equipment is damaged.')) {
        // 收集设备损坏信息
        var damages = [];
        $(`.damage-info[data-request-id="${requestID}"]`).each(function () {
            var equipmentID = $(this).data('equipment-id');
            var isDamaged = $(this).find('.damage-status').is(':checked');
            var damageDescription = $(this).find('.damage-description').val();

            if (isDamaged && damageDescription) {
                damages.push({
                    equipmentID: equipmentID,
                    description: damageDescription
                });
            }
        });

        // 发起 AJAX 请求处理返回
        $.ajax({
            url: baseUrl + '/manageReturn',
            type: 'POST',
            contentType: 'application/json', // 发送 JSON 数据
            data: JSON.stringify({requestID: requestID, damages: damages}),
            success: function (response) {
                if (response.status === 'success') {
                    alert('Return has been successfully managed.');
                    loadReturnsData(); // 刷新数据
                } else {
                    alert('Failed to manage return: ' + response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error('Error managing return:', error);
                alert('Failed to manage return: ' + xhr.responseText);
            }
        });
    }
}

function submitReturnConfirmation() {
    console.log("Submitting return confirmation.");
    var selectedEquipment = [];
    $('#equipmentList input:checked').each(function () {
        selectedEquipment.push($(this).val());
    });

    var damageDescription = $('#damageDescription').val();
    var requestData = {
        equipment: selectedEquipment,
        damageDescription: damageDescription
    };

    $.post('/path/to/submitReturnConfirmation', requestData, function (response) {
        console.log("Return processed successfully:", response);
        alert('Return processed successfully.');
        $('#confirmReturnModal').modal('hide');
        location.reload(); // Reload the page to update the display
    }).fail(function () {
        console.error('Failed to process the return.');
    });
}

