<script>
	import Navbar from '$lib/components/navbar.svelte';

	export let data;
</script>

<Navbar account={data.user} />

<div class="flex h-screen flex-row items-center justify-center text-3xl font-bold">
	{#if data.user === undefined}
		Hola, desconocido
	{:else}
		Hola, {data.user.id}
	{/if}
</div>
