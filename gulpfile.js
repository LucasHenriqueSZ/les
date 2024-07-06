const gulp = require('gulp');
const sass = require('gulp-sass')(require('sass'));
const path = require('path');

const paths = {
    styles: {
        src: 'src/main/resources/static/scss/custom.scss',
        dest: 'src/main/resources/static/css/'
    },
    scripts: {
        src: [
            'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js',
            'node_modules/@popperjs/core/dist/umd/popper.min.js',
        ],
        dest: 'src/main/resources/static/js/'
    }
};

gulp.task('sass', function () {
    return gulp.src(paths.styles.src)
        .pipe(sass({
            includePaths: [path.join(__dirname, 'node_modules')]
        }).on('error', sass.logError))
        .pipe(gulp.dest(paths.styles.dest));
});

gulp.task('scripts', function () {
    return gulp.src(paths.scripts.src)
        .pipe(gulp.dest(paths.scripts.dest));
});

gulp.task('watch', function () {
    gulp.watch('src/main/resources/static/scss/**/*.scss', gulp.series('sass'));
});

gulp.task('default', gulp.series('sass', 'scripts', 'watch'));
