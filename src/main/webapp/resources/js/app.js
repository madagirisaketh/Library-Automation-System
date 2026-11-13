document.addEventListener('DOMContentLoaded', function () {

  // ----- Books page: search + category chips + availability filter -----
  var grid = document.getElementById('bookGrid');
  if (grid) {
    var cards = grid.querySelectorAll('.book-card');
    var search = document.getElementById('bookSearch');
    var avail = document.getElementById('bookAvail');
    var chips = document.querySelectorAll('#bookChips .chip');
    var noResults = document.getElementById('noResults');
    var activeCat = '';

    var applyFilters = function () {
      var q = search.value.toLowerCase().trim();
      var shown = 0;
      cards.forEach(function (c) {
        var ok = (!activeCat || c.dataset.cat === activeCat) &&
                 (!avail.value || c.dataset.avail === avail.value) &&
                 (!q || c.textContent.toLowerCase().indexOf(q) > -1);
        c.style.display = ok ? '' : 'none';
        if (ok) shown++;
      });
      noResults.style.display = (shown === 0) ? 'block' : 'none';
    };

    search.addEventListener('input', applyFilters);
    avail.addEventListener('change', applyFilters);
    chips.forEach(function (chip) {
      chip.addEventListener('click', function () {
        chips.forEach(function (c) { c.classList.remove('active'); });
        chip.classList.add('active');
        activeCat = chip.dataset.cat;
        applyFilters();
      });
    });
  }

  // ----- Tables: search box (+ optional status filter) -----
  var tbl = document.getElementById('tbl');
  var tblSearch = document.getElementById('tblSearch');
  var tblStatus = document.getElementById('tblStatus');
  if (tbl && tblSearch) {
    var rows = tbl.querySelectorAll('tbody tr');
    var filterRows = function () {
      var q = tblSearch.value.toLowerCase().trim();
      var st = tblStatus ? tblStatus.value : '';
      rows.forEach(function (r) {
        var ok = (!q || r.textContent.toLowerCase().indexOf(q) > -1) &&
                 (!st || r.dataset.status === st);
        r.style.display = ok ? '' : 'none';
      });
    };
    tblSearch.addEventListener('input', filterRows);
    if (tblStatus) tblStatus.addEventListener('change', filterRows);
  }

  // ----- Book form: live cover preview from ISBN -----
  var isbn = document.getElementById('isbnInput');
  var preview = document.getElementById('coverPreview');
  if (isbn && preview) {
    var updatePreview = function () {
      var v = isbn.value.replace(/[^0-9Xx]/g, '');
      if (v.length >= 10) {
        preview.style.display = 'block';
        preview.src = 'https://covers.openlibrary.org/b/isbn/' + v + '-M.jpg?default=false';
      } else {
        preview.style.display = 'none';
      }
    };
    preview.onerror = function () { preview.style.display = 'none'; };
    isbn.addEventListener('input', updatePreview);
    updatePreview();
  }

  // ----- Fade out success/error messages -----
  document.querySelectorAll('.alert').forEach(function (a) {
    setTimeout(function () {
      a.style.opacity = '0';
      setTimeout(function () { a.remove(); }, 450);
    }, 5000);
  });
});