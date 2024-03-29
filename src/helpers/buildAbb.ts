import { exec, spawn } from 'child_process';

import { rename, copyFile } from 'fs/promises';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';


export const __filename = fileURLToPath(import.meta.url);
export const __dirname = dirname(__filename);
import {
    debugApkDir,
    newAppSourceCodeDir,
    outputApksDir,
    releaseAbbDir,
} from '../constants.js';

export let buildAbb = (orderId, apkFileName, newVersionCode) => {
    return new Promise<{
        status: 'success' | 'error';
        msg: string;
        data?: {
            outputAbb: string;
            abbNewName: string;
        };
    }>(async (resolve, reject) => {
        try {
            console.log(__dirname);
            console.log('newAppSourceCodeDir', newAppSourceCodeDir);

            const command = 'gradle wrapper';
            const args = ['--daemon', ':app:bundleRelease'];
            const options = { cwd: newAppSourceCodeDir };

            const gradleProcess = spawn('bash', [
                `${newAppSourceCodeDir}/buildAbb.sh`,
            ]);

            gradleProcess.stdout.on('data', (data) => {
                console.log(`stdout: ${data}`);
                if (data.includes('-Xlint:deprecation ')) {
                } else {
                    return;
                }
            });

            gradleProcess.stderr.on('data', (data) => {
                console.error(`stderr: ${data}`);
            });

            gradleProcess.on('error', (error) => {
                console.error(`Error executing command: ${error.message}`);
                reject({
                    status: 'error',
                    msg: `Error executing command: ${error.message}`,
                });
            });

            gradleProcess.on('close', async (code) => {
                console.log(`Command exited with code ${code}`);

                if (code === 0) {
                    // APK file generated, do something with it here
                    const releaseAbb = join(releaseAbbDir, 'app-release.aab');
                    const abbNewName = `${apkFileName}.aab`;
                    const renamedAbb = join(releaseAbbDir, abbNewName);
                    const outputAbb = join(outputApksDir, abbNewName);
                    try {
                        await rename(releaseAbb, renamedAbb);
                    } catch (error) {
                        console.error(`Error renaming file: ${error.message}`);
                        reject(error);
                    }

                    console.log('Rename complete!');

                    try {
                        await copyFile(renamedAbb, outputAbb);
                    } catch (error) {
                        console.error(`Error copying file: ${error.message}`);
                        reject(error);
                    }

                    console.log('renamedApk was copied to output folder');

                    console.log('APK generated');

                    // upload apk to ftp
                    try {
                        resolve({
                            status: 'success',
                            msg: `APK generated`,
                            data: { outputAbb, abbNewName },
                        });
                    } catch (error) {
                        console.error(`Error uploading APK: ${error.message}`);
                        reject(error);
                    }
                } else {
                    console.error('Command exited with non-zero status');
                    reject(
                        new Error(
                            `Command exited with non-zero status: ${code}`
                        )
                    );
                }
            });
        } catch (error) {
            reject(error);
        }
    });
};
