/**
 * Created by Denis on 11.06.2017.
 */


$(document).ready(main);

function main() {

    $('.person-details-btn').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var $collapse = $this.closest('.collapse-group').find('.collapse');
        $collapse.collapse('toggle');
    });
}
