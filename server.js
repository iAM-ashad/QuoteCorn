const http = require('http');

const htmlContent = `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>QuoteCorn — Capture What Moves You</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body { background-color: #131313; color: #FFFFFF; font-family: 'Inter', sans-serif; min-height: 100vh; display: flex; flex-direction: column; align-items: center; }
    .container { width: 100%; max-width: 600px; padding: 20px; position: relative; }
    
    /* Header Monolith */
    .brand-header { text-align: center; margin: 24px 0 16px; }
    .monogram { font-family: 'Playfair Display', serif; font-size: 36px; color: #D4AF37; }
    .brand-title { font-family: 'Playfair Display', serif; font-size: 38px; font-weight: 700; letter-spacing: 0.12em; margin: 4px 0 10px; }
    .tagline-row { display: flex; align-items: center; justify-content: center; gap: 10px; }
    .line { width: 32px; height: 1px; background-color: rgba(212, 175, 55, 0.5); }
    .tagline { color: #D4AF37; font-size: 10px; font-weight: 600; letter-spacing: 0.2em; }
    
    /* Search & Tags */
    .search-box { width: 100%; padding: 12px 16px; background: transparent; border: 1px solid rgba(255, 255, 255, 0.2); color: #FFF; font-family: 'Inter', sans-serif; font-size: 14px; margin-bottom: 16px; outline: none; }
    .search-box:focus { border-color: #D4AF37; }
    .tags-row { display: flex; gap: 10px; overflow-x: auto; margin-bottom: 20px; padding-bottom: 4px; }
    .tag-chip { padding: 6px 14px; border: 1px solid rgba(255, 255, 255, 0.2); font-size: 11px; cursor: pointer; text-transform: uppercase; white-space: nowrap; }
    .tag-chip.active { background: #D4AF37; color: #3C2F00; font-weight: 600; border-color: #D4AF37; }
    
    /* Feed Cards */
    .feed { display: flex; flex-direction: column; gap: 16px; }
    .card { background: linear-gradient(180deg, #131313, #1A1A1A, #131313); border: 1px solid rgba(212, 175, 55, 0.3); padding: 20px; cursor: pointer; transition: transform 0.2s; }
    .card:hover { transform: translateY(-2px); }
    .quote-text { font-family: 'Playfair Display', serif; font-size: 18px; line-height: 1.5; color: #D4AF37; margin-bottom: 12px; }
    .author-text { font-size: 11px; font-weight: 500; letter-spacing: 0.15em; text-transform: uppercase; color: rgba(255,255,255,0.8); }
    
    /* FAB */
    .fab { position: fixed; bottom: 28px; right: 28px; width: 56px; height: 56px; background: #D4AF37; color: #3C2F00; font-family: 'Playfair Display', serif; font-size: 28px; display: flex; align-items: center; justify-content: center; cursor: pointer; border: none; font-weight: bold; }
    
    /* Overlay Views */
    .view { display: none; position: fixed; inset: 0; background: #131313; z-index: 10; padding: 20px; flex-direction: column; }
    .view.active { display: flex; }
    
    /* Editor View */
    .editor-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; }
    .btn-text { background: none; border: none; color: rgba(255,255,255,0.7); font-family: 'Inter'; font-size: 12px; cursor: pointer; text-transform: uppercase; }
    .btn-primary { background: #D4AF37; color: #3C2F00; border: none; padding: 8px 20px; font-size: 12px; font-weight: 600; cursor: pointer; }
    .input-field { width: 100%; padding: 12px; background: transparent; border: 1px solid rgba(255,255,255,0.2); color: #FFF; margin-bottom: 12px; font-family: 'Inter'; }
    .live-preview { border: 1px solid #D4AF37; padding: 20px; margin-top: 16px; background: #000; text-align: center; }
    
    /* Focus Detail View */
    .focus-canvas { flex: 1; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; padding: 40px 20px; }
    .bottom-sheet { position: absolute; bottom: 0; left: 0; right: 0; background: rgba(19, 19, 19, 0.98); border-top: 1px solid rgba(212, 175, 55, 0.3); padding: 20px; }
    .sheet-title { font-size: 12px; letter-spacing: 0.1em; margin-bottom: 12px; display: flex; justify-content: space-between; }
    .swatches { display: flex; gap: 10px; overflow-x: auto; margin: 12px 0; }
    .swatch { padding: 8px 12px; border: 1px solid rgba(255,255,255,0.2); font-size: 10px; cursor: pointer; white-space: nowrap; }
    .swatch.active { border-color: #D4AF37; }
    .action-row { display: flex; gap: 12px; margin-top: 16px; }
    .btn-delete { flex: 1; border: 1px solid rgba(255, 180, 171, 0.6); background: none; color: #FFFFB4AB; padding: 12px; font-size: 11px; cursor: pointer; }
    .btn-export { flex: 1.5; background: #D4AF37; color: #3C2F00; border: none; padding: 12px; font-size: 11px; font-weight: 600; cursor: pointer; }

    /* Dialog */
    .dialog-overlay { display: none; position: fixed; inset: 0; background: rgba(0,0,0,0.8); z-index: 100; align-items: center; justify-content: center; }
    .dialog-overlay.active { display: flex; }
    .dialog-box { background: #131313; border: 1px solid #D4AF37; padding: 24px; width: 90%; max-width: 400px; text-align: center; }

    /* Snackbar */
    .snackbar { position: fixed; bottom: 20px; left: 50%; transform: translateX(-50%); background: #131313; border: 1px solid #D4AF37; padding: 12px 20px; font-size: 12px; color: #FFF; display: none; z-index: 200; }
    .snackbar.active { display: block; }
  </style>
</head>
<body>
  <div class="container" id="app">
    <!-- Gallery Screen -->
    <div id="galleryScreen">
      <div class="brand-header">
        <div class="monogram">“ ”</div>
        <h1 class="brand-title">QUOTECORN</h1>
        <div class="tagline-row">
          <div class="line"></div>
          <span class="tagline">CAPTURE WHAT MOVES YOU</span>
          <div class="line"></div>
        </div>
      </div>

      <input type="text" class="search-box" placeholder="Search quotes, authors, or sources..." id="searchInput" />

      <div class="tags-row">
        <div class="tag-chip active" onclick="filterTag(this, 'ALL')">ALL</div>
        <div class="tag-chip" onclick="filterTag(this, 'PHILOSOPHY')">PHILOSOPHY</div>
        <div class="tag-chip" onclick="filterTag(this, 'MINDFULNESS')">MINDFULNESS</div>
        <div class="tag-chip" onclick="filterTag(this, 'CREATIVITY')">CREATIVITY</div>
      </div>

      <div class="feed" id="quoteFeed">
        <div class="card" onclick="openFocus('Waste no more time arguing about what a good man should be. Be one.', 'MARCUS AURELIUS')">
          <div class="quote-text">“Waste no more time arguing about what a good man should be. Be one.”</div>
          <div class="author-text">MARCUS AURELIUS</div>
        </div>
      </div>

      <button class="fab" onclick="openEditor()">+</button>
    </div>

    <!-- Create Quote Editor Screen -->
    <div id="editorScreen" class="view">
      <div class="editor-header">
        <button class="btn-text" onclick="closeEditor()">CANCEL</button>
        <span style="font-size: 14px; font-weight: bold;">NEW QUOTE</span>
        <button class="btn-primary" onclick="saveQuote()">SAVE</button>
      </div>

      <textarea class="input-field" rows="4" placeholder="Type or paste your quote here..." id="quoteInput" oninput="updatePreview()"></textarea>
      <input type="text" class="input-field" placeholder="Author (e.g. Marcus Aurelius)" id="authorInput" oninput="updatePreview()" />
      <input type="text" class="input-field" placeholder="Source / Book / Speech (Optional)" id="sourceInput" />

      <div class="live-preview">
        <div class="quote-text" id="previewQuoteText">“Type your quote above...”</div>
        <div class="author-text" id="previewAuthorText">AUTHOR</div>
      </div>
    </div>

    <!-- Focus Detail View -->
    <div id="focusScreen" class="view">
      <div class="focus-canvas" onclick="toggleBottomSheet()">
        <div class="quote-text" style="font-size: 24px;" id="focusQuoteText"></div>
        <div class="author-text" style="margin-top: 16px;" id="focusAuthorText"></div>
      </div>

      <div class="bottom-sheet" id="bottomSheet">
        <div class="sheet-title">
          <span>QUOTE OPTIONS</span>
          <span style="color: #D4AF37;" id="activeThemeLabel">CREATOR'S CHOICE</span>
        </div>
        <div style="font-size: 10px; color: rgba(255,255,255,0.6); margin-top: 8px;">SELECT VISUAL THEME</div>
        <div class="swatches">
          <div class="swatch active" onclick="selectTheme(this, 'CREATOR\'S CHOICE')">CREATOR'S CHOICE</div>
          <div class="swatch" onclick="selectTheme(this, 'AURELIAN MONOLITH')">AURELIAN MONOLITH</div>
          <div class="swatch" onclick="selectTheme(this, 'MIDNIGHT OBSIDIAN')">MIDNIGHT OBSIDIAN</div>
        </div>
        <div class="action-row">
          <button class="btn-delete" onclick="showDeleteDialog()">DELETE QUOTE</button>
          <button class="btn-export">EXPORT PNG IMAGE</button>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation Dialog -->
    <div class="dialog-overlay" id="deleteDialog">
      <div class="dialog-box">
        <div style="font-family: 'Playfair Display'; font-size: 32px; color: #FFFFB4AB;">“ ”</div>
        <h3 style="font-family: 'Playfair Display'; font-size: 20px; margin: 8px 0;">DELETE QUOTE</h3>
        <p style="font-size: 12px; color: rgba(255,255,255,0.7); margin-bottom: 20px;">Are you sure you want to remove this quote from your sanctuary? This action cannot be undone.</p>
        <div class="action-row">
          <button class="btn-delete" style="color: #FFF; border-color: rgba(255,255,255,0.3);" onclick="hideDeleteDialog()">CANCEL</button>
          <button class="btn-delete" style="background: #B3261E; color: #FFF; border-color: #FFFFB4AB;" onclick="confirmDelete()">DELETE</button>
        </div>
      </div>
    </div>

    <!-- Snackbar -->
    <div class="snackbar" id="snackbar">Quote captured successfully to your sanctuary.</div>
  </div>

  <script>
    function openEditor() {
      document.getElementById('editorScreen').classList.add('active');
    }
    function closeEditor() {
      document.getElementById('editorScreen').classList.remove('active');
    }
    function updatePreview() {
      const q = document.getElementById('quoteInput').value || 'Type your quote above...';
      const a = document.getElementById('authorInput').value || 'AUTHOR';
      document.getElementById('previewQuoteText').innerText = '“' + q + '”';
      document.getElementById('previewAuthorText').innerText = a.toUpperCase();
    }
    function saveQuote() {
      const q = document.getElementById('quoteInput').value;
      const a = document.getElementById('authorInput').value || 'Anonymous';
      if (!q) return;
      
      const feed = document.getElementById('quoteFeed');
      const card = document.createElement('div');
      card.className = 'card';
      card.onclick = function() { openFocus(q, a); };
      card.innerHTML = '<div class="quote-text">“' + q + '”</div><div class="author-text">' + a.toUpperCase() + '</div>';
      feed.prepend(card);

      closeEditor();
      showSnackbar();
    }
    function showSnackbar() {
      const s = document.getElementById('snackbar');
      s.classList.add('active');
      setTimeout(() => s.classList.remove('active'), 4000);
    }
    function openFocus(q, a) {
      document.getElementById('focusQuoteText').innerText = '“' + q + '”';
      document.getElementById('focusAuthorText').innerText = a.toUpperCase();
      document.getElementById('focusScreen').classList.add('active');
    }
    function selectTheme(el, name) {
      document.querySelectorAll('.swatch').forEach(s => s.classList.remove('active'));
      el.classList.add('active');
      document.getElementById('activeThemeLabel').innerText = name;
    }
    function showDeleteDialog() {
      document.getElementById('deleteDialog').classList.add('active');
    }
    function hideDeleteDialog() {
      document.getElementById('deleteDialog').classList.remove('active');
    }
    function confirmDelete() {
      hideDeleteDialog();
      document.getElementById('focusScreen').classList.remove('active');
    }
    function filterTag(el, tag) {
      document.querySelectorAll('.tag-chip').forEach(t => t.classList.remove('active'));
      el.classList.add('active');
    }
  </script>
</body>
</html>`;

const server = http.createServer((req, res) => {
  res.writeHead(200, { 'Content-Type': 'text/html' });
  res.end(htmlContent);
});

server.listen(8080, () => {
  console.log('QuoteCorn Web Runner listening on http://localhost:8080');
});
