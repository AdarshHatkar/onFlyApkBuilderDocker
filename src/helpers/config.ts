
export const builderEnvironment = process.env.BUILDER_ENVIRONMENT || 'local';

export let isLocalEnvironnement: boolean;
console.log(builderEnvironment);
if (builderEnvironment == 'local') {
    isLocalEnvironnement = true

} else {
    isLocalEnvironnement = false
}


export let restBaseUrl: string;
export let apkHome: string;




if (isLocalEnvironnement == true) {
    restBaseUrl = 'http://127.0.0.1:3005/web2Apk/v1';
} else {
    restBaseUrl = 'https://mtaapi.primexop.com/web2Apk/v1';


    
}

