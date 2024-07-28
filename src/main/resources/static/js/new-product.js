let isFirstImage = true;

function addImage(event) {
    const fileInput = event.target;

    if (fileInput.files && fileInput.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            if (isFirstImage) {
                const firstImage = document.getElementById('firstImage');
                firstImage.src = e.target.result;
                document.getElementById('firstDeleteIcon').style.display = 'block';
                isFirstImage = false;
            } else {
                const imgWrapper = document.createElement('div');
                imgWrapper.className = 'position-relative m-2';

                const img = document.createElement('img');
                img.src = e.target.result;
                img.style.width = '150px';
                img.className = 'img-thumbnail';

                const deleteIcon = document.createElement('i');
                deleteIcon.className = 'ri-delete-bin-7-fill position-absolute text-danger';
                deleteIcon.style.top = '5px';
                deleteIcon.style.right = '5px';
                deleteIcon.style.cursor = 'pointer';
                deleteIcon.onclick = function () {
                    imgWrapper.remove();
                };

                imgWrapper.appendChild(img);
                imgWrapper.appendChild(deleteIcon);

                const imageContainer = document.getElementById('imageContainer');
                imageContainer.appendChild(imgWrapper);
            }
        };

        reader.readAsDataURL(fileInput.files[0]);
    }
}

function removeFirstImage() {
    document.getElementById('firstImage').src = '/img/placeholders/placeholder-img.jpg';
    document.getElementById('firstDeleteIcon').style.display = 'none';
    isFirstImage = true;
}
