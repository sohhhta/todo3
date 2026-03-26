// モーダルの制御JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // モーダル要素の取得
    const modal = document.getElementById('videoModal');
    const video = document.getElementById('demoVideo');
    const closeBtn = document.getElementById('closeModal');

    // すべてのカード要素を取得
    const cards = document.querySelectorAll('.card[data-video-url]');

    // 各カードにクリックイベントリスナーを追加
    cards.forEach(card => {
        card.addEventListener('click', function() {
            const videoUrl = this.getAttribute('data-video-url');
            if (videoUrl) {
                // 動画のソースを設定
                video.src = videoUrl;

                // モーダルを表示
                modal.style.display = 'flex';

                // 動画を再生
                video.play();
            }
        });
    });

    // 閉じるボタンのクリックイベント
    closeBtn.addEventListener('click', closeModal);

    // モーダル外クリックで閉じる
    modal.addEventListener('click', function(event) {
        if (event.target === modal) {
            closeModal();
        }
    });

    // モーダルを閉じる関数
    function closeModal() {
        // 動画を停止
        video.pause();
        video.currentTime = 0;

        // モーダルを非表示
        modal.style.display = 'none';

        // 動画ソースをクリア
        video.src = '';
    }
});