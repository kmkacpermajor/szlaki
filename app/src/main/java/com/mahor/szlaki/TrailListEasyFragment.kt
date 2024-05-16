package com.mahor.szlaki

class TrailListEasyFragment : TrailListFragment() {
    override fun observeTrails(adapter: TrailListAdapter) {
        trailViewModel.easyTrails.observe(viewLifecycleOwner) { trails ->
            trails.let { adapter.submitList(it)}
        }
    }
}